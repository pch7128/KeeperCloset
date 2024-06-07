package com.pch7128.keepercloset.dto;

import java.sql.Date;

import org.springframework.stereotype.Component;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Component
@Data
@RequiredArgsConstructor
@Builder
public class RvResponseDTO {

	private int rvnum;
	private String reservation_no;
	private java.sql.Date startdate;
	private java.sql.Date enddate;
	private String rv_name;
	private String boxsize;
	private int rv_cnt;
	private int rv_price;
	private Member member;
	private StorageDTO store;
	private Review review;
	
	@Builder
	public RvResponseDTO(Reservation entity) {
		super();
		this.rvnum = entity.getRvnum();
		this.reservation_no = entity.getReservation_no();
		this.startdate = entity.getStartdate();
		this.enddate = entity.getEnddate();
		this.rv_name =entity.getRv_name();
		this.boxsize = entity.getBoxsize();
		this.rv_cnt = entity.getRv_cnt();
		this.rv_price = entity.getRv_price();
		this.member = entity.getMember();
		this.store = entity.getStore();
		this.review = entity.getReview();
	}
	
	
	
}
