package com.pch7128.keepercloset.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
public class Address {
	
	@Id
    @SequenceGenerator(sequenceName="SEQ_ADD", allocationSize=1, name="SEQ_ADD")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_ADD")	
	private int add_num;
	
	private int add_zipcode;
	
	private String addr;
	
	private String add_detail;
	
	private String add_ref;
	
	private String whole_add;
	
	@ManyToOne
	@JoinColumn(name="unum")
	private Member member;

	public Address(int add_zipcode, String addr, String add_detail, String add_ref, String whole_add,
			Member member) {
		
		this.add_zipcode = add_zipcode;
		this.addr = addr;
		this.add_detail = add_detail;
		this.add_ref = add_ref;
		this.whole_add = whole_add;
		this.member = member;
	}
	
	
	
}
