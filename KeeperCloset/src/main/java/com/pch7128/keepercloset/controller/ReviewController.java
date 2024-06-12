package com.pch7128.keepercloset.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pch7128.keepercloset.dto.ReviewResponseDTO;
import com.pch7128.keepercloset.svc.BoardSvc;

@Controller
@RequestMapping("/review")
public class ReviewController {
	

	@Autowired
	private BoardSvc bSvc;
	
	@GetMapping("/list")
	public String getReviewList(@PageableDefault(page=0 , size=10) Pageable pg,  Model m) {
		
		Page<ReviewResponseDTO> reviews=bSvc.reviewPaging(pg);
		m.addAttribute("r", reviews);
		return "kc/review/reviewList";
	}
	
	@GetMapping("/review-detail/{r_bnum}")
	public String getReview(@PathVariable("r_bnum") int r_bnum, Model m) {
		
		return "kc/review/reviewDetail";
	}
}
