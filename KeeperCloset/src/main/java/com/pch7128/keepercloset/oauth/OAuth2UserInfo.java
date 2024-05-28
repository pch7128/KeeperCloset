package com.pch7128.keepercloset.oauth;

import org.springframework.stereotype.Repository;

@Repository
public interface OAuth2UserInfo {
	
	 String getProviderId();
	 String getProvider();
	 String getEmail();
}
