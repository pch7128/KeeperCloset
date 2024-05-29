package com.pch7128.keepercloset.dto;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Component
@Data
@RequiredArgsConstructor
@Entity
@Table(name="reservation")
public class Reservation {
	
	@Id
	@SequenceGenerator(sequenceName="SEQ_RVNUM", allocationSize=1, name="SEQ_RVNUM")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_RVNUM")
	private int rvnum;
	private int unum;
	private String startdate;
	private String enddate;
	private String rv_name;
	private int stnum;
	private String boxsize;
	private int rv_price;
	private int rv_cnt;
	

}
