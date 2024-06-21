package com.pch7128.keepercloset.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pch7128.keepercloset.dto.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer>{
	
	@Query("SELECT c FROM Comment c WHERE c.inq.inq_num=:inq_num")
	Comment findByinq_num(@Param("inq_num") int inq_num);

	
}
