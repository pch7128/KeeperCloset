package com.pch7128.keepercloset.oauth;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class kakaoInfo implements OAuth2UserInfo{
	
	@Autowired
	private Map<String, Object> attributes;

	@Override
	public String getProviderId() {
		
		return attributes.get("id").toString();
	}

	@Override
	public String getProvider() {
		
		return "kakao";
	}

	@Override
	public String getEmail() {
		
		return (String)((Map) attributes.get("kakao_account")).get("email");
		
	}

	public kakaoInfo(Map<String, Object> attributes) {
		super();
		this.attributes = attributes;
	}
	
	

}
