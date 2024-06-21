package com.pch7128.keepercloset.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pch7128.keepercloset.dto.Comment;
import com.pch7128.keepercloset.dto.Inquiry;
import com.pch7128.keepercloset.dto.InquiryResponseDTO;
import com.pch7128.keepercloset.dto.RvResponseDTO;
import com.pch7128.keepercloset.svc.AdminSvc;
import com.pch7128.keepercloset.svc.BoardSvc;

@Controller
@RequestMapping("/keeper/admin")
public class AdminController {

	
	@Autowired
	private AdminSvc aSvc;
	@Autowired
	private BoardSvc bSvc;
	
	@GetMapping("/inq/inqList")
	public String inqListPage(Model m) {
		
		List<InquiryResponseDTO> inqdto =aSvc.getInqList();
		
		m.addAttribute("inq", inqdto);
	
		return "kc/admin/adminInquiry/inqList";
	}
	
//	@GetMapping("/inq/inqList")
//	public String inqListPage(@PageableDefault(page = 0, size = 5) Pageable pg,Model m) {
//		Page<InquiryResponseDTO> inqdto=aSvc.inqListPaging(pg);
//		
//		int nowP=inqdto.getNumber()+1;
//		int totalP=inqdto.getTotalPages();
//		int startP = Math.max(nowP - 2, 1);
//		int endP = Math.min(startP + 4, totalP);
//		startP = Math.max(1, endP - 4);
//		
//		m.addAttribute("inq", inqdto);
//		m.addAttribute("startP", startP);
//		m.addAttribute("endP", endP);
//		m.addAttribute("nowP", nowP);
//		return "kc/admin/adminInquiry/inqList";
//	}
	
	@GetMapping("/inq/inq_detail/{inq_num}")
	public String inqDetailPage(@PathVariable("inq_num") int inq_num,Model m) {
		InquiryResponseDTO inqDTO=bSvc.getInq(inq_num);
		m.addAttribute("inq", inqDTO);
		return "kc/admin/adminInquiry/inqDetail";
	}
	
	@GetMapping("/inq/commentform/{inq_num}")
	public String commentformPage(@PathVariable("inq_num") int inq_num,Model m) {
		
		InquiryResponseDTO inq=bSvc.getInq(inq_num);
		RvResponseDTO rvDTO=aSvc.getRv(inq.getMember().getUnum());
		m.addAttribute("i", inq);
		m.addAttribute("rv", rvDTO);
		return "kc/admin/adminInquiry/commentForm";
	}
	
	@PostMapping("/inq/addComment/{inq_num}")
	@ResponseBody
	public Map<String,Object> addComment(@PathVariable("inq_num") int inq_num, Comment c){
		
		boolean ok=aSvc.addComment(inq_num, c);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("ok", ok);
		
		return map;
	}
	
	@GetMapping("/inq/editComment/{inq_num}")
	public String editCommPage(@PathVariable("inq_num") int inq_num,Model m) {
		InquiryResponseDTO inq=bSvc.getInq(inq_num);
		RvResponseDTO rvDTO=aSvc.getRv(inq.getMember().getUnum());
		m.addAttribute("i", inq);
		m.addAttribute("rv", rvDTO);
		
		return "kc/admin/adminInquiry/commentEdit";
	}
	
	@PostMapping("/inq/editComment/{inq_num}")
	@ResponseBody
	public Map<String,Object> editComment(@PathVariable("inq_num") int inq_num, Comment c){
		Map<String,Object> map = new HashMap<String, Object>();
		boolean ok=aSvc.editComm(inq_num, c);
		System.out.println(ok);
		map.put("ok", ok);
		return map;
	}
	
}
