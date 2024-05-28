package com.pch7128.keepercloset.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pch7128.keepercloset.dto.BoxSizeDTO;
import com.pch7128.keepercloset.dto.StorageDTO;
import com.pch7128.keepercloset.repository.RvMapper;
import com.pch7128.keepercloset.repository.RvRepository;

@Controller
@RequestMapping("/rv")
public class RvController {
	
	@Autowired
	private RvMapper rvm;
	
	@GetMapping("/keeping")
	public String bookingPage(Model m) {
			
		return "kc/rv/booking";
	}
	
	
	
	
	

}
