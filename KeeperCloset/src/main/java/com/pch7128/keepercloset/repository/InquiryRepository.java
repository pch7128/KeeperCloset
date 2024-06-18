package com.pch7128.keepercloset.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pch7128.keepercloset.dto.Inquiry;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Integer> {

	@Query("SELECT i FROM Inquiry i WHERE i.member.unum=:unum")
	Page<Inquiry> findByUnum(@Param("unum") int unum,Pageable pageable);
}
