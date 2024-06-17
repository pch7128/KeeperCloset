package com.pch7128.keepercloset.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.pch7128.keepercloset.dto.PrincipalDetails;
import com.pch7128.keepercloset.dto.Review;
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
	public String getReview(@PathVariable("r_bnum") int r_bnum, Model m,
			@AuthenticationPrincipal PrincipalDetails principal ) {
		if(principal != null) {
			int unum=principal.getMember().getUnum();
			m.addAttribute("unum", unum);
		} else {
			m.addAttribute("unum", "");
		}
		
		ReviewResponseDTO r=bSvc.getReview(r_bnum);
		m.addAttribute("r", r);
		
		return "kc/review/reviewDetail";
	}
	
	@GetMapping("/review-editform/{r_bnum}")
	public String getEditForm(@PathVariable("r_bnum") int r_bnum,Model m) {
		ReviewResponseDTO r=bSvc.getReview(r_bnum);
		m.addAttribute("r", r);
		return "kc/review/reviewEdit";
	}
	
	@PostMapping("/review-editform/{r_bnum}")
	@ResponseBody
	public Map<String,Object> getEditForm(@PathVariable("r_bnum") int r_bnum,
			@RequestParam("rvnum") int rvnum,
			@RequestParam(value="files") MultipartFile[] files,Review review,@AuthenticationPrincipal PrincipalDetails principal) {
		System.out.println("수정하는 리뷰번호 :"+review.getR_bnum());
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			boolean ok=bSvc.editReview(files, review, principal.getMember(),rvnum);
			map.put("ok",ok);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("ok", false);
		}
		return map;
	}
	
	@PostMapping("/delete/{r_bnum}")
	@ResponseBody
	public Map<String,Object> deleteReview(@PathVariable("r_bnum") int r_bnum){
		
		boolean result=bSvc.deleteReview(r_bnum);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("deleted", result);
		return map;
		
	}
}
