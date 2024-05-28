package com.pch7128.keepercloset.dto;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Component
@Data
@RequiredArgsConstructor
@Entity
public class Reservation {
	
	@Id
	private int rvnum;
	private int unum;
	private String startdate;
	private String enddate;
	private String rv_name;
	private String stnum;
	private String boxsize;
	private int rv_price;
	private int rv_cnt;
	

}
