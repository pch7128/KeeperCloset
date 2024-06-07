package com.pch7128.keepercloset.svc;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pch7128.keepercloset.dto.JoinDTO;
import com.pch7128.keepercloset.dto.Member;
import com.pch7128.keepercloset.dto.MemberDTO;
import com.pch7128.keepercloset.dto.PrincipalDetails;
import com.pch7128.keepercloset.dto.Reservation;
import com.pch7128.keepercloset.dto.RvResponseDTO;
import com.pch7128.keepercloset.repository.MemberRepository;
import com.pch7128.keepercloset.repository.RvRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;



@Service
public class MemberSvc implements UserDetailsService{
	
	@Autowired
	private MemberRepository mr;
	@Autowired
	private BCryptPasswordEncoder enc;
	@Autowired
	private RvRepository rvre;
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Member member =mr.findById(email);
		
		if(member==null) {
			throw new UsernameNotFoundException("일치하는 사용자 정보 없음");
		}
		return new PrincipalDetails(member);
	}
	
	public boolean join(JoinDTO j) {
		
		Member newMember = new Member()
				.builder()
				.name(j.getName())	
				.id(j.getU_email())
				.u_pwd(enc.encode(j.getU_pwd()))
				.utel(j.getU_tel())
				.uauthority(j.getU_authority())
				.build();
		Member m=mr.save(newMember);
		if(m==null) {
			return false;
		}
		
		return true;
	}
	
	public Member getRvlist(int unum){
		
		Optional<Member> opM=mr.findById(unum);
		Member m=opM.get();
		List<Reservation> rvlist=opM.get().getRvlist();
		return m;
	}
	
	public Reservation getRv(int rvnum) {
		Optional<Reservation> rv=rvre.findById(rvnum);
		return rv.get();
	}

	public Page<MemberDTO> paging(int unum,Pageable pageable){
		
		 //member를 찾아오는 jpal문
		 String jpql = "SELECT m FROM Member m JOIN FETCH m.rvlist r WHERE m.unum = :unum";
		 
		 Member member = entityManager.createQuery(jpql, Member.class)
                 .setParameter("unum", unum)
                 .getSingleResult();
		
		 //한명의 회원이 예약한 리스트를 페이징
		 List<Reservation> rvlist=member.getRvlist();
		 List<Reservation> pgRvlist=rvlist.stream()
		 		.skip(pageable.getOffset())
		 		.limit(pageable.getPageSize())
		 		.collect(Collectors.toList());
		 
		 List<MemberDTO> mDTO = pgRvlist.stream()
				 						.map(reservation ->{
				 							MemberDTO dto = new MemberDTO();
				 							dto.setUnum(member.getUnum());
				 							dto.setName(member.getName());
				 							dto.setUtel(member.getUtel());
				 							dto.setRvlist(member.getRvlist());
				 							dto.setReviewlist(member.getReviewlist());
				 							dto.setStore(reservation.getStore());
				 							return dto;
				 						}).collect(Collectors.toList());
		 
		 
		 
		 return new PageImpl<>(mDTO,pageable,rvlist.size());
	}
	
}
