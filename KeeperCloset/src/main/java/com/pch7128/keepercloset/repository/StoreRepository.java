package com.pch7128.keepercloset.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pch7128.keepercloset.dto.StorageDTO;

@Repository
public interface StoreRepository extends JpaRepository<StorageDTO, Integer> {

}
