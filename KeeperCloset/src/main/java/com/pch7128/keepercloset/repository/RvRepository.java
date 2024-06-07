package com.pch7128.keepercloset.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pch7128.keepercloset.dto.BoxSizeDTO;
import com.pch7128.keepercloset.dto.Member;
import com.pch7128.keepercloset.dto.Reservation;

@Repository
public interface RvRepository extends JpaRepository<Reservation, Integer>{

	
}
