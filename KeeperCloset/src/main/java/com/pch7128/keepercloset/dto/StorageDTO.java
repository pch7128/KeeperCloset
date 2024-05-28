package com.pch7128.keepercloset.dto;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Component
@Data
@RequiredArgsConstructor
@Entity
public class StorageDTO {

	@Id
	private int stnum;
	private String st_name;
	private String st_address;
	private String st_phone;
	
}
