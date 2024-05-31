package com.pch7128.keepercloset.svc;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pch7128.keepercloset.dto.JoinDTO;
import com.pch7128.keepercloset.dto.Member;
import com.pch7128.keepercloset.dto.PrincipalDetails;
import com.pch7128.keepercloset.dto.Reservation;
import com.pch7128.keepercloset.repository.MemberRepository;
import com.pch7128.keepercloset.repository.RvRepository;

@Service
public class MemberSvc implements UserDetailsService{
	
	@Autowired
	private MemberRepository mr;
	@Autowired
	private BCryptPasswordEncoder enc;
	@Autowired
	private RvRepository rvre;

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
	

}
