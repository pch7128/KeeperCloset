package com.pch7128.keepercloset.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.pch7128.keepercloset.dto.Member;
import com.pch7128.keepercloset.dto.PrincipalDetails;
import com.pch7128.keepercloset.repository.MemberRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService{
	
	@Autowired
	private MemberRepository mr;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);
		
		OAuth2UserInfo oauth2userinfo = null;
		String provider = userRequest.getClientRegistration().getRegistrationId();
		
		if(provider.equals("kakao")) {
			oauth2userinfo = new kakaoInfo(oAuth2User.getAttributes());
		}
		
		String providerId = oauth2userinfo.getProviderId();
		String email = oauth2userinfo.getEmail();
		Member member = mr.findById(email);
		
		if(member==null) {
			member = member.builder()
							.id(email)
							.provider(provider)
							.providerid(providerId)
							.uauthority("ROLE_USER")
							.build();
			mr.save(member);
		}
		return new PrincipalDetails(member,oAuth2User.getAttributes());
	}
	
	
}
