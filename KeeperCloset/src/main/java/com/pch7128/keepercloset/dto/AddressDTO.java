package com.pch7128.keepercloset.dto;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Component
@Data
@RequiredArgsConstructor
public class AddressDTO {
	
	private int add_zipcode;
	private String addr;
	private String add_detail;
	private String add_ref;
	private String whole_add=this.addr+" "+this.add_detail+" "+this.add_ref;
	public AddressDTO(int add_zipcode, String addr, String add_detail, String add_ref, String whole_add) {
		super();
		this.add_zipcode = add_zipcode;
		this.addr = addr;
		this.add_detail = add_detail;
		this.add_ref = add_ref;
		this.whole_add = whole_add;
	}

	
}
