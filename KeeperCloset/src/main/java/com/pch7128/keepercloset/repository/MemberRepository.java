package com.pch7128.keepercloset.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pch7128.keepercloset.dto.JoinDTO;
import com.pch7128.keepercloset.dto.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member,Integer>{
	
	Member findById(String email);
	

}
