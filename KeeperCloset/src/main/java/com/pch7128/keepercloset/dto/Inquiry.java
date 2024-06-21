package com.pch7128.keepercloset.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Entity
@Table(name="inquiry_board")
public class Inquiry {

	@Id
	@SequenceGenerator(sequenceName="SEQ_INQNUM", allocationSize=1, name="SEQ_INQNUM")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_INQNUM")
	private int inq_num;
	
	private String inq_title;
	
	private String inq_content;
	
	private java.sql.Date inq_date;
	
	private boolean is_deleted=false;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="unum")
	private Member member;
	
	@OneToOne(mappedBy="inq",fetch=FetchType.LAZY)
	private Comment comm;
	
}
