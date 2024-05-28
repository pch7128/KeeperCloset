package com.pch7128.keepercloset.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/keep")
public class MainController {

	@GetMapping("/main")
	public String main(Model m) {
		m.addAttribute("test", "test");
		return "kc/main/kcMain";
	}
	
	
	
}
