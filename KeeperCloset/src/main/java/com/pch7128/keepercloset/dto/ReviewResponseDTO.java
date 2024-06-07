package com.pch7128.keepercloset.dto;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Component
@Data
@RequiredArgsConstructor
public class ReviewResponseDTO {
	
	private int r_bnum;
	private String board_title;
	private java.sql.Date board_posted;
	private String board_content;
	private Member member;
	private Reservation reservation;
	private List<BattachDTO> battach;
	
	public ReviewResponseDTO(Review entity) {
		super();
		this.r_bnum = entity.getR_bnum();
		this.board_title = entity.getBoard_title();
		this.board_posted = entity.getBoard_posted();
		this.board_content = entity.getBoard_content();
		this.member = entity.getMember();
		this.reservation = entity.getReservation();
		this.battach = entity.getBattach();
	}
	
	

}
