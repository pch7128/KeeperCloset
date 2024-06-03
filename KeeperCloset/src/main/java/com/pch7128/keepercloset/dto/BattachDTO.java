package com.pch7128.keepercloset.dto;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Component
@Data
@RequiredArgsConstructor
@Entity
@Table(name="board_attach")
@Builder
public class BattachDTO {

	@Id
	@SequenceGenerator(sequenceName="SEQ_FNUM", allocationSize=1, name="SEQ_FNUM_GEN")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_FNUM_GEN")	
	private int fnum;
	
	private int rbnum;
	
	private String fname;
	
	private String savedfname;
	
    @ManyToOne
    @JoinColumn(name = "rbnum")
    private Review review;

    @Builder
	public BattachDTO(int fnum, int rbnum, String fname, String savedfname, Review review) {
		super();
		this.fnum = fnum;
		this.rbnum = rbnum;
		this.fname = fname;
		this.savedfname = savedfname;
		this.review = review;
	}
	
	
}
