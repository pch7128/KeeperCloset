package com.pch7128.keepercloset.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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


@Data
@RequiredArgsConstructor
@Entity
public class Reservation {
	
	@Id
	@SequenceGenerator(sequenceName="SEQ_RVNUM", allocationSize=1, name="SEQ_RVNUM")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_RVNUM")
	private int rvnum;
	
	private String reservation_no;
	
	private java.sql.Date startdate;
	
	private java.sql.Date enddate;
	
	private String rv_name;


	private String boxsize;

	private int rv_cnt;
	
	private int rv_price;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="unum")
	private Member member;
	
	@ManyToOne
	@JoinColumn(name="stnum" )
	private StorageDTO store;	
	
	@OneToOne(mappedBy = "reservation", fetch=FetchType.LAZY)
	private Review review;
	
}
