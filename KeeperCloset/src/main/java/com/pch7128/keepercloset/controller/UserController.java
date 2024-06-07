package com.pch7128.keepercloset.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
	public String rvCheck(@PageableDefault(page=0, size=5)Pageable pg,
			Model m, @AuthenticationPrincipal PrincipalDetails principal) {
	
		int unum=principal.getMember().getUnum();
		Page<MemberDTO> mdto=mSvc.paging(unum,pg);
		
		int nowP=mdto.getNumber()+1;
		int totalP=mdto.getTotalPages();
		int startP = Math.max(nowP - 2, 1);
		int endP = Math.min(startP + 4, totalP);
		startP = Math.max(1, endP - 4);
		m.addAttribute("m", mdto);
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
}
