package com.pch7128.keepercloset.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pch7128.keepercloset.dto.Battach;

@Repository
public interface BattachRepository extends JpaRepository<Battach, Integer>{

	@Query("SELECT a FROM Battach a WHERE a.review.r_bnum=:r_bnum")
	List<Battach> getBattach(@Param("r_bnum") int r_bnum);
	
	@Query("DELETE FROM Battach a WHERE a.review.r_bnum=:r_bnum")
	int deleteBattach(@Param("r_bnum") int r_bnum);
}
