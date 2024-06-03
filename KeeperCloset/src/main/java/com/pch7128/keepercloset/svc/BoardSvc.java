package com.pch7128.keepercloset.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pch7128.keepercloset.dto.Review;
import com.pch7128.keepercloset.repository.RvRepository;

@Service
public class BoardSvc {
	
	@Autowired
	private RvRepository rvre;
	@Autowired
	private static final String upPath="";
	
	public void saveReview(MultipartFile[] files,Review review) {
		
	}

}
