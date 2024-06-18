package com.pch7128.keepercloset.dto;

import java.sql.Date;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Component
@Data
@RequiredArgsConstructor
public class InquiryResponseDTO {

	private int inq_num;
	private String inq_title;
	private String inq_content;
	private java.sql.Date inq_date;
	private Member member;
	private CommentDTO comm;
	
	public InquiryResponseDTO(Inquiry entity) {
		super();
		this.inq_num = entity.getInq_num();
		this.inq_title = entity.getInq_title();
		this.inq_content = entity.getInq_content();
		this.inq_date = entity.getInq_date();
		this.member = entity.getMember();
		if(entity.getComm()!=null) {
			this.comm = new CommentDTO(entity.getComm());
		}
		
	}
	
	
}
