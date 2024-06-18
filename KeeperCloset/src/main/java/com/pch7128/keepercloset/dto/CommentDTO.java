package com.pch7128.keepercloset.dto;

import java.sql.Date;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Component
@Data
@RequiredArgsConstructor
public class CommentDTO {
	
	private int cm_num;
	private String cm_content;
	private java.sql.Date cm_date;
	
	public CommentDTO(Comment entity) {
		super();
		this.cm_num = entity.getCm_num();
		this.cm_content = entity.getCm_content();
		this.cm_date = entity.getCm_date();
		
	}
	
	
	
}
