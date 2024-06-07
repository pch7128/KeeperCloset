package com.pch7128.keepercloset.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Component
@Data
@RequiredArgsConstructor
@Entity
@Table(name="storage_t")
public class StorageDTO {

	@Id
	private int stnum;
	private String st_name;
	private String st_address;
	private String st_phone;
	
	@OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
	private List<Reservation> rvlist;
}
