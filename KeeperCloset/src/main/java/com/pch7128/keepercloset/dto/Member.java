package com.pch7128.keepercloset.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Builder
@Component
@Data
@RequiredArgsConstructor
@Entity
public class Member {
	
	@Id
    @SequenceGenerator(sequenceName="SEQ_USER", allocationSize=1, name="SEQ_USER_GEN")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_USER_GEN")
	private int unum;
	
	
	private String name;
	
	
	private String id;

	
	private String u_pwd;
	
	@Column(name="u_tel")
	private String utel;
	
	@Column(name="u_authority")
	private String uauthority;
	
	
	private String provider;
	
	@Column(name="provider_id")
	private String providerid;
	
	@OneToMany(mappedBy = "member" ,cascade = CascadeType.ALL)
	private List<Reservation> rvlist;
	

	@OneToMany(mappedBy = "member")
	private List<Review> reviewlist;

	@Builder
	public Member(int unum, String name, String u_pwd, String id, String utel, String uauthority,
			String provider, String providerid) {
		super();
		this.unum = unum;
		this.name = name;
		this.u_pwd = u_pwd;
		this.id = id;
		this.utel = utel;
		this.uauthority = uauthority;
		this.provider = provider;
		this.providerid = providerid;
	}
	
	
	
}
