package com.pch7128.keepercloset.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
@Component
@Entity
@Table(name="review_board")
public class Review {
	
	@Id
    @SequenceGenerator(sequenceName="SEQ_RBNUM", allocationSize=1, name="SEQ_REVIEW_GEN")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_REVIEW_GEN")
	private int r_bnum;
	 
	private String board_title;
	
	private java.sql.Date board_posted;
	
	private String board_content;
	
	@Column(name="rbhits")
	private int hits;
	
	@ManyToOne
	@JoinColumn(name="unum")
	private Member member;
	
	@OneToOne
	@JoinColumn(name="rvnum")
	private Reservation reservation;
	
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "review")
    private List<Battach> battach;

}
