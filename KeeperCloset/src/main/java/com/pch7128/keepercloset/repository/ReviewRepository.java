package com.pch7128.keepercloset.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pch7128.keepercloset.dto.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

	@Query("SELECT DISTINCT r FROM Review r LEFT JOIN FETCH r.battach")
	Page<Review> findWithBattach(Pageable pageable);
	
	@Query("SELECT r FROM Review r WHERE r.reservation.rvnum=:rvnum")
	Review findByRbNum(@Param("rvnum") int rvnum);
	
	
}
