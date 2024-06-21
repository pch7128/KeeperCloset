package com.pch7128.keepercloset.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pch7128.keepercloset.dto.BoxSizeDTO;
import com.pch7128.keepercloset.dto.Member;
import com.pch7128.keepercloset.dto.Reservation;

@Repository
public interface RvRepository extends JpaRepository<Reservation, Integer>{

	
	@Query(value = "SELECT * FROM reservation r WHERE r.rvnum = (SELECT MAX(r2.rvnum) FROM reservation r2 WHERE r2.unum = :unum)", nativeQuery = true)
	Reservation findByCurrent(@Param("unum") int unum);
}
