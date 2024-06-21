package com.pch7128.keepercloset.svc;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pch7128.keepercloset.dto.Comment;
import com.pch7128.keepercloset.dto.CommentDTO;
import com.pch7128.keepercloset.dto.Inquiry;
import com.pch7128.keepercloset.dto.InquiryResponseDTO;
import com.pch7128.keepercloset.dto.Reservation;
import com.pch7128.keepercloset.dto.RvResponseDTO;
import com.pch7128.keepercloset.repository.CommentRepository;
import com.pch7128.keepercloset.repository.InquiryRepository;
import com.pch7128.keepercloset.repository.RvRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class AdminSvc {
	
	@Autowired
	private CommentRepository cmre;
	@Autowired
	private InquiryRepository inqre;
	@Autowired
	private RvRepository rvre;
	@Autowired
	private BoardSvc bSvc;
	@PersistenceContext
	private EntityManager entityManager;
	
	public Page<InquiryResponseDTO> inqListPaging(Pageable pageable){
		
		Page<Inquiry> inqList=inqre.findWithComm(pageable);
		
		
		String countJpql = "SELECT COUNT(i) FROM Inquiry i";
		long total=entityManager.createQuery(countJpql,Long.class)
		.getSingleResult();
		
		List<InquiryResponseDTO> inqDTO = inqList.stream()
				.map(inquiry ->{
					InquiryResponseDTO dto = new InquiryResponseDTO(inquiry);
					
					if(inquiry.getComm()!=null) {
						CommentDTO cmDTO = new CommentDTO(inquiry.getComm());
						dto.setComm(cmDTO);
					}
					return dto;
				})
				.collect(Collectors.toList());
		
		return  new PageImpl<>(inqDTO,pageable,total);
	}
	
	
	
	public boolean addComment(int inq_num,Comment c) {
		
		Inquiry inq=inqre.findById(inq_num).orElseThrow();
		LocalDate currentDate = LocalDate.now();
		c.setCm_date(Date.valueOf(currentDate));
		c.setInq(inq);
		cmre.save(c);
	
		return true;
	
	}
	
	public boolean editComm(int inq_num,Comment c) {
		Inquiry inquiry = inqre.findById(inq_num)
              .orElseThrow();
		Comment editC = inquiry.getComm();
		System.out.println(editC.getCm_num());
		
		editC.setCm_content(c.getCm_content());
		LocalDate currentDate = LocalDate.now();
		editC.setCm_date(Date.valueOf(currentDate));
		inquiry.setComm(editC);
		inqre.save(inquiry);
		
		return true;
	}
	
	public RvResponseDTO getRv(int unum) {
		
		Reservation r=rvre.findByCurrent(unum);
		
		return new RvResponseDTO(r);
	}

	public List<InquiryResponseDTO> getInqList(){
		
		List<Inquiry> inq=inqre.findAll();
		
		List<InquiryResponseDTO> inqDTO = inq.stream()
				.map(inquiry ->{
					InquiryResponseDTO dto = new InquiryResponseDTO(inquiry);
					
					if(inquiry.getComm()!=null) {
						CommentDTO cmDTO = new CommentDTO(inquiry.getComm());
						dto.setComm(cmDTO);
					}
					return dto;
				})
				.collect(Collectors.toList());
		
		return inqDTO;
	}
}
