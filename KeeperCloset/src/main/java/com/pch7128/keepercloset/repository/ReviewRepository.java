package com.pch7128.keepercloset.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pch7128.keepercloset.dto.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

}
