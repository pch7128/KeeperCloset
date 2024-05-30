package com.pch7128.keepercloset.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pch7128.keepercloset.dto.PrincipalDetails;

@Controller
@RequestMapping("/keep")
public class MainController {

	@GetMapping("/main")
	public String main(Model m,@AuthenticationPrincipal PrincipalDetails pd) {
		
		if(pd!=null) {
			m.addAttribute("unum", pd.getMember().getUnum());
		}
		
		return "kc/main/kcMain";
	}
	
	
	
}
