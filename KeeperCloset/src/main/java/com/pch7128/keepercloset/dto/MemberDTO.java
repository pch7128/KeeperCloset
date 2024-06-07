package com.pch7128.keepercloset.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Component
public class MemberDTO {

	private int unum;
	private String name;
	private String utel;
	private List<Reservation> rvlist;
	private List<Review> reviewlist;
	private StorageDTO store;	
}
