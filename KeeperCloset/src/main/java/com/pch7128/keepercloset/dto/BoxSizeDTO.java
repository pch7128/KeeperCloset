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
@Table(name="boxsize_t")
public class BoxSizeDTO {
	
	@Id
	private int snum;
	private String sizeop;
	private int size_price;
	
	
	

}
