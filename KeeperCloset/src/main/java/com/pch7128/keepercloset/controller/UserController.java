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
import org.springframework.web.bind.annotation.RequestBody;
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
	public String loginForm(Model m) {
		m.addAttribute("title", "KeeperCloset-로그인");
		m.addAttribute("ct", "kc/login/loginForm");
		m.addAttribute("cCss", "/static/css/login/loginForm.css");
		return "common/layouts/loginLayout";
	}

	@GetMapping("/signup")
	public String joinForm(Model m) {
		m.addAttribute("title", "KeeperCloset-회원가입");
		m.addAttribute("ct", "kc/login/joinForm");
		m.addAttribute("cCss", "/static/css/login/joinForm.css");
		return "common/layouts/loginLayout";
	}
	
	@PostMapping("/signup/idcheck")
	@ResponseBody
	public Map<String, Boolean> idchecking(@RequestParam("id") String id){
		Map<String, Boolean> map = new HashMap<>();
		boolean result=mSvc.joinId(id);
		map.put("result", result);
		return map;
	}
	
	@PostMapping("/signup")
	public String join(JoinDTO j, Model m){
		
		System.out.println(j.getAddlist().size());
		boolean result=mSvc.join(j);
		if(result){
			m.addAttribute("title", "KeeperCloset-회원가입");
			m.addAttribute("ct", "kc/login/signUpS");

			return "common/layouts/loginLayout";
		} else {
			m.addAttribute("title", "KeeperCloset-회원가입");
			m.addAttribute("ct", "kc/login/signUpF");
			return "common/layouts/loginLayout";
		}
		
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
		
		m.addAttribute("title", "KeeperCloset 마이페이지-예약 현황");
		m.addAttribute("ct", "kc/mypage/bookingList");
		m.addAttribute("cCss", "/static/css/mypage/bookingList.css");		
		
		
		return "common/layouts/mypageLayout";
	}
	
	@GetMapping("/review/write/{rvnum}")
	public String reviewForm(@PathVariable("rvnum") int rvnum,Model m) {
		Reservation rv=mSvc.getRv(rvnum);
		m.addAttribute("rv", rv);
		m.addAttribute("title", "KeeperCloset 이용후기");
		m.addAttribute("ct", "kc/review/reviewForm");
		m.addAttribute("cCss", "/static/css/review/reviewForm.css");
		
		return "common/layouts/reviewLayout";
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
		m.addAttribute("title", "KeeperCloset 이용후기");
		m.addAttribute("ct", "kc/review/reviewResult");
//		m.addAttribute("cCss", "/static/css/review/reviewForm.css");
		return "common/layouts/reviewLayout";
	}
	
	//1:1 문의리스트
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
		
		m.addAttribute("title", "KeeperCloset 1:1문의");
		m.addAttribute("ct", "kc/mypage/userInquiry");
		m.addAttribute("cCss", "/static/css/mypage/userInquiry.css");
		
		return "common/layouts/mypageLayout";
	}
	
	//1:1 문의 작성
	@GetMapping("/mypage/addinquiry")
	public String inquiryform(Model m) {
		m.addAttribute("title", "KeeperCloset 1:1문의");
		m.addAttribute("ct", "kc/mypage/userInquiryForm");
		m.addAttribute("cCss", "/static/css/mypage/userInquiryForm.css");
		return "common/layouts/mypageLayout";
	}
	//1:1 문의 작성
	@PostMapping("/mypage/addinquiry")
	public String writeInquiry(Inquiry inq,@AuthenticationPrincipal PrincipalDetails principal,Model m) {
		boolean ok=bSvc.addInquiry(principal.getMember(), inq);
		if(ok) {
			m.addAttribute("title", "KeeperCloset 1:1문의");
			m.addAttribute("ct", "kc/mypage/userInquiryS");

		} else {
			m.addAttribute("title", "KeeperCloset 1:1문의");
			m.addAttribute("ct", "kc/mypage/userInquiryF");

		}
		return "common/layouts/mypageLayout";
	}
	
	//1:1 문의디테일
	@GetMapping("/mypage/inquiry_detail/{inq_num}")
	public String inqDetailPage(@PathVariable("inq_num") int inq_num,Model m) {
		InquiryResponseDTO inq=bSvc.getInq(inq_num);
		m.addAttribute("inq", inq);
		m.addAttribute("title", "KeeperCloset 1:1문의");
		m.addAttribute("ct", "kc/mypage/userInquiryDetail");
		m.addAttribute("cCss", "/static/css/mypage/userInquiryDetail.css");
		return "common/layouts/mypageLayout";
	}
	
	//1:1 문의 수정
	@GetMapping("/mypage/inquiry_edit/{inq_num}")
	public String inqEditPage(@PathVariable("inq_num") int inq_num,Model m) {
		InquiryResponseDTO inq=bSvc.getInq(inq_num);
		m.addAttribute("i", inq);
		m.addAttribute("title", "KeeperCloset 1:1문의");
		m.addAttribute("ct", "kc/mypage/userInquiryEdit");
		m.addAttribute("cCss", "/static/css/mypage/userInquiryEdit.css");
		return "common/layouts/mypageLayout";
	}
	
	@PostMapping("/mypage/inquiry_edit/{inq_num}")
	public String inqEdit(@PathVariable("inq_num") int inq_num,Inquiry i,Model m) {
		i.setInq_num(inq_num);
		boolean ed=bSvc.inqEdit(i);
		if(ed) {
			m.addAttribute("title", "KeeperCloset 1:1문의");
			m.addAttribute("ct", "kc/mypage/userInquiryS");

		} else {
			m.addAttribute("title", "KeeperCloset 1:1문의");
			m.addAttribute("ct", "kc/mypage/userInquiryF");
		}
			return "common/layouts/mypageLayout";
	}
	
	//1:1 문의 삭제
	@PostMapping("/mypage/inquiry-delete/{inq_num}")
	@ResponseBody
	public Map<String, Object> inqDelete(@PathVariable("inq_num") int inq_num){
		Map<String, Object> map = new HashMap<String, Object>();
		boolean result=bSvc.inqDelete(inq_num);
		map.put("result", result);
		
		return map;
	}
	
	
}
