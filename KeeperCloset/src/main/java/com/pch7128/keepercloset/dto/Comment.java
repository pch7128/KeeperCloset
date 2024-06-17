package com.pch7128.keepercloset.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Entity
@Table(name="inq_comment")
public class Comment {
	
	@Id
	@SequenceGenerator(sequenceName="SEQ_CMNUM", allocationSize=1, name="SEQ_CMNUM")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_CMNUM")
	private int cm_num;
	
	private String cm_content;
	
	private java.sql.Date cm_date;
	
	@OneToOne
	@JoinColumn(name="inq_num")
	private Inquiry inq;

}
