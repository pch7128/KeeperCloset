package com.pch7128.keepercloset.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.pch7128.keepercloset.dto.Inquiry;
import com.pch7128.keepercloset.dto.InquiryResponseDTO;
import com.pch7128.keepercloset.dto.JoinDTO;
import com.pch7128.keepercloset.dto.Member;
import com.pch7128.keepercloset.dto.MemberDTO;
import com.pch7128.keepercloset.dto.PrincipalDetails;
import com.pch7128.keepercloset.dto.Reservation;
import com.pch7128.keepercloset.dto.Review;
import com.pch7128.keepercloset.dto.RvResponseDTO;
import com.pch7128.keepercloset.svc.BoardSvc;
import com.pch7128.keepercloset.svc.MemberSvc;
import com.pch7128.keepercloset.svc.RvSvc;

@Controller
@RequestMapping("/user")
public class UserController {
	
	
	@Autowired
	private MemberSvc mSvc;
	@Autowired
	private RvSvc rSvc;
	@Autowired
	private BoardSvc bSvc;
	
	@GetMapping("/login")
	public String loginForm() {
		return "kc/login/loginForm";
	}

	@GetMapping("/signup")
	public String joinForm() {
		return "kc/login/JoinForm";
	}
	
	@PostMapping("/signup")
	@ResponseBody
	public Map<String, Boolean> join(JoinDTO j, Model m){
		
		boolean result=mSvc.join(j);
		System.out.println("회원가입 결과:"+result);
		Map<String,Boolean> map = new HashMap<>();
		map.put("result", result);
		
		return map;
	}
	
	@GetMapping("/mypage")
	public String mypageMain(Model m, @AuthenticationPrincipal PrincipalDetails principal) {
		
		
		return "kc/mypage/userInfo";
	}

	@GetMapping("/mypage/checkdetails")
	public String rvCheck(@PageableDefault(page = 0, size = 5) Pageable pg,
			Model m, @AuthenticationPrincipal PrincipalDetails principal) {
	
		
		int unum=principal.getMember().getUnum();
		
		Page<RvResponseDTO> rvdto=mSvc.rvpaging(unum,pg);
		
		int nowP=rvdto.getNumber()+1;
		int totalP=rvdto.getTotalPages();
		int startP = Math.max(nowP - 2, 1);
		int endP = Math.min(startP + 4, totalP);
		startP = Math.max(1, endP - 4);
		System.out.println("현재: "+nowP+"총페이지 수:"+totalP
				+"시작페이지: "+startP+"끝페이지 :"+endP);
		
		m.addAttribute("rv", rvdto);
		m.addAttribute("startP", startP);
		m.addAttribute("endP", endP);
		m.addAttribute("nowP", nowP);
		
		return "kc/mypage/bookingList";
	}
	
	@GetMapping("/review/write/{rvnum}")
	public String reviewForm(@PathVariable("rvnum") int rvnum,Model m) {
		Reservation rv=mSvc.getRv(rvnum);
		m.addAttribute("rv", rv);
		return "kc/review/reviewForm";
	}
	
	@PostMapping("/review/addreview/{rvnum}")
	@ResponseBody
	public Map<String,Object> addReview(@PathVariable("rvnum") int rvnum,
			@AuthenticationPrincipal PrincipalDetails principal,
				@RequestParam(value="files") MultipartFile[] files,Review r) {
		Member member=principal.getMember();
		Reservation rv=mSvc.getRv(rvnum);
		r.setReservation(rv);
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			bSvc.saveReview(files, r, member);
			map.put("ok",true);
			
		} catch (Exception e) {
			e.printStackTrace();
			map.put("ok", false);
		}
		
		return map;
	}
	
	@GetMapping("/review/result/{rvnum}")
	public String getResultPage(@PathVariable("rvnum") int rvnum, Model m) {
		int r_bnum=bSvc.getReviewNum(rvnum);
		System.out.println("리뷰번호 :"+r_bnum);
		m.addAttribute("r_bnum", r_bnum);	
		return "kc/review/reviewResult";
	}
	
	@GetMapping("/mypage/inquiry")
	public String inquiryPage(@PageableDefault(page = 0, size = 5) Pageable pg,
			@AuthenticationPrincipal PrincipalDetails principal,Model m) {
		
		Page<InquiryResponseDTO> inqDTO=bSvc.inquriyPaging(pg,principal.getMember());
		int nowP=inqDTO.getNumber()+1;
		int totalP=inqDTO.getTotalPages();
		int startP = Math.max(nowP - 2, 1);
		int endP = Math.min(startP + 4, totalP);
		startP = Math.max(1, endP - 4);
		
		m.addAttribute("inq", inqDTO);
		m.addAttribute("startP", startP);
		m.addAttribute("endP", endP);
		m.addAttribute("nowP", nowP);
		return "kc/mypage/userInquiry";
	}
	
	@GetMapping("/mypage/addinquiry")
	public String inquiryform(Model m) {
		return "kc/mypage/userInquiryForm";
	}
	
	@PostMapping("/mypage/addinquiry")
	public String writeInquiry(Inquiry inq,@AuthenticationPrincipal PrincipalDetails principal) {
		boolean ok=bSvc.addInquiry(principal.getMember(), inq);
		if(ok) {
			return "kc/mypage/userInquiryS";
		} else
		return "kc/mypage/userInquiryF";
	}
	
}
