package com.pch7128.keepercloset.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pch7128.keepercloset.dto.BattachDTO;

@Repository
public interface BattachRepository extends JpaRepository<BattachDTO, Integer>{

}