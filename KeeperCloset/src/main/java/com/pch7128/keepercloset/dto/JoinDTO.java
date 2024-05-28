package com.pch7128.keepercloset.dto;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Component
@Data
@RequiredArgsConstructor
public class JoinDTO {
	
	private String name;
	private String u_email;
	private String u_pwd;
	private String u_tel;
	private String u_authority;

	
	

}
