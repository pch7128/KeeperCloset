package com.pch7128.keepercloset.dto;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Component
@Data
@RequiredArgsConstructor
public class PrincipalDetails implements UserDetails, OAuth2User{
	
	
	private Member member;
	//attributes는 소셜로그인을 통해서 받은 정보들을 그대로 담아 return 해주는 역할
	private Map<String, Object> attributes;
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return null;
	}

	@Override
	public String getPassword() {
	
		return member.getU_pwd();
	}

	@Override
	public String getUsername() {
	
		return member.getId();
	}

	@Override
	public boolean isAccountNonExpired() {
		
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
	
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
	
		return true;
	}

	@Override
	public boolean isEnabled() {
	
		return true;
	}

	public PrincipalDetails(Member member) {
		super();
		this.member = member;
	}

	
	@Override
	public Map<String, Object> getAttributes() {
		// TODO Auto-generated method stub
		return attributes;
	}

	@Override
	public String getName() {
		
		return null;
	}

	public PrincipalDetails(Member member, Map<String, Object> attributes) {
		super();
		this.member = member;
		this.attributes = attributes;
	}

	
	
	
}
