package com.pch7128.keepercloset.dto;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Component
@Data
@RequiredArgsConstructor
public class BattachDTO {

	private int fnum;
	private String fname;
	private String savedfname;
	
	public BattachDTO(Battach entity) {
		
		this.fnum = entity.getFnum();
		this.fname = entity.getFname();
		this.savedfname = entity.getSavedfname();
	}
	
	
	
}
