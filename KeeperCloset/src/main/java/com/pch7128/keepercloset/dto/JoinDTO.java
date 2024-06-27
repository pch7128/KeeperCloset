package com.pch7128.keepercloset.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Component
@Data
@RequiredArgsConstructor
public class JoinDTO {
	
	private String u_name;
	private String u_id;
	private String u_pwd;
	private List<AddressDTO> addlist= new ArrayList<>();
	private String u_tel;	
	private String u_authority;

	
	

}
