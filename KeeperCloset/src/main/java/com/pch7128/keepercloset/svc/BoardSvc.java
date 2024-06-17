package com.pch7128.keepercloset.svc;


import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pch7128.keepercloset.dto.Battach;
import com.pch7128.keepercloset.dto.BattachDTO;
import com.pch7128.keepercloset.dto.Member;
import com.pch7128.keepercloset.dto.Reservation;
import com.pch7128.keepercloset.dto.Review;
import com.pch7128.keepercloset.repository.BattachRepository;
import com.pch7128.keepercloset.repository.ReviewRepository;
import com.pch7128.keepercloset.repository.RvRepository;
import com.pch7128.keepercloset.dto.ReviewResponseDTO;
import com.pch7128.keepercloset.exception.ResourceNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class BoardSvc {
	
	@Autowired
	private ReviewRepository reviewre;
	@Autowired
	private BattachRepository bAttre;
	@Autowired
	private MemberSvc mSvc;
	@Autowired
	private static final String RUpPath="/Users/eeeun/Desktop/git/KeeperCloset/KeeperCloset/src/main/resources/static/images/review";
	
	
	@Transactional
	public void saveReview(MultipartFile[] files,Review review, Member m) throws Exception{
		review.setMember(m);
		List<Battach> flist=battachList(files,review);
		review.setBattach(flist);
		LocalDate currentDate = LocalDate.now();
		review.setBoard_posted(Date.valueOf(currentDate));
		Review r=reviewre.save(review);
		
	}

	public List<Battach> battachList(MultipartFile[] files,Review review) throws IllegalStateException,IOException{
		
		List<Battach> flist = new ArrayList<Battach>();
		
		for(MultipartFile mf : files) {
			if(mf.isEmpty()) break;
			
			Battach bAtt = new Battach();
			UUID u=UUID.randomUUID();
			String fname=mf.getOriginalFilename();
			String savedfname=u.toString()+"_"+fname;
			bAtt = bAtt.builder()
					.fname(fname)
					.savedfname(savedfname)
					.review(review)
					.build();
			flist.add(bAtt);
			mf.transferTo(new File(RUpPath,bAtt.getSavedfname()));
			
		}
		bAttre.saveAll(flist);
		return flist;
	}
	
	@Transactional
	public boolean editReview(MultipartFile[] files,Review review,Member m,int rvnum) throws Exception{
		
		Review r=reviewre.findById(review.getR_bnum()).orElseThrow();
		Reservation rv=mSvc.getRv(rvnum);
		r.setMember(m);
		r.setReservation(rv);		
		List<Battach> flist=battachList(files,review);
		r.setBattach(flist);
		r.setBoard_content(review.getBoard_content());
		LocalDate currentDate = LocalDate.now();
		r.setBoard_posted(Date.valueOf(currentDate));
		reviewre.save(r);
		return true;
	}
	
	public ReviewResponseDTO getReview(int r_bnum) {
		
		Review review=reviewre.findById(r_bnum).orElseThrow();
		List<BattachDTO> bdto=getImg(r_bnum);
		review.setHits(review.getHits()+1);
		reviewre.save(review);
		ReviewResponseDTO rdto=new ReviewResponseDTO(review);
		rdto.setBattach(bdto);
		return rdto;
	}
	
	public int getReviewNum(int rvnum) {
		
		Review r=reviewre.findByRbNum(rvnum);
		if(r != null) {
			return r.getR_bnum();
		}
		throw new ResourceNotFoundException("Review not found with rvnum " + rvnum);
		
	}
	
	@Transactional
	public boolean deleteReview(int r_bnum) {
		Review r=reviewre.findById(r_bnum).orElseThrow();
		if(r.getBattach()!=null && !r.getBattach().isEmpty()) {
			boolean delefileImg=deleteImg(r_bnum);
			if(delefileImg) {
				reviewre.deleteById(r_bnum);
			}
		}
		
		reviewre.deleteById(r_bnum);
		return true;
	}
	
	public List<BattachDTO> getImg(int r_bnum) {
		
		List<Battach> b=bAttre.getBattach(r_bnum);
		List<BattachDTO> bdto=b.stream()
				.map(battach -> new BattachDTO(battach))
				.collect(Collectors.toList());
		
		return bdto;
	}
	
	
	
	public boolean deleteImg(int r_bnum) {
		
		List<Battach> battList=bAttre.getBattach(r_bnum);
		
		for(Battach b :battList) {
			bAttre.delete(b);
			deleteFile(b);
			return true;
		}
		return false;
	}
	
	public boolean deleteFile(Battach b) {
		
		File f = new File(RUpPath+"/"+b.getSavedfname());
		System.out.println(RUpPath+b.getSavedfname());
		if(f.exists()) {
			return f.delete();
		}
		return false;
	}
	
	
	//Page 처리
	public Page<ReviewResponseDTO> reviewPaging(Pageable pageable){
		
		Page<Review> reviews=reviewre.findWithBattach(pageable);
		
		List<ReviewResponseDTO> reviewDTO = reviews.stream()
				.map(review -> {
					ReviewResponseDTO dto = new ReviewResponseDTO(review);
					List<BattachDTO> bttDTO = review.getBattach().stream()
							.map(BattachDTO::new)
							.collect(Collectors.toList());
					dto.setBattach(bttDTO);
					return dto;
				}).collect(Collectors.toList());
		
		return new PageImpl<>(reviewDTO,pageable,reviews.getTotalElements());
	}
	
	
}
