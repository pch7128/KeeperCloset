package com.pch7128.keepercloset.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pch7128.keepercloset.dto.JoinDTO;
import com.pch7128.keepercloset.dto.Member;
import com.pch7128.keepercloset.dto.PrincipalDetails;
import com.pch7128.keepercloset.dto.Reservation;
import com.pch7128.keepercloset.svc.MemberSvc;

@Controller
@RequestMapping("/user")
public class UserController {
	
	
	@Autowired
	private MemberSvc mSvc;
	
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

	@GetMapping("/mypage/checkdetails/{unum}")
	public String rvCheck(Model m, @AuthenticationPrincipal PrincipalDetails principal
			, @PathVariable("unum") int unum) {
		
		List<Reservation> rvlist=mSvc.getRvlist(unum);

		m.addAttribute("m", rvlist);
		
		return "kc/mypage/bookingList";
	}
}
