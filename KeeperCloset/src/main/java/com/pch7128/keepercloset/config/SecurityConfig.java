package com.pch7128.keepercloset.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.pch7128.keepercloset.oauth.PrincipalOauth2UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	@Autowired
	private final PrincipalOauth2UserService pos;
	
	@Bean
	public static BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder enc = new BCryptPasswordEncoder();

		return enc;
	}
	
	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return (webSecurity) -> webSecurity.ignoring().requestMatchers("/resources/**","/static/**", "/images/**","/ignore2");
	}
	
	@Bean 
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		

		http.authorizeHttpRequests(authz -> authz
				.requestMatchers("/", "/keep", "/user/signup", "/keep/main","/review/list",
						"/review/review-detail/**","/keep/denied","/logout", "/static/**", "/images/**").permitAll()
				.requestMatchers("/keep/main").hasAnyRole("USER", "ADMIN")
				.requestMatchers("/keep/admin","/keep/admin/**").hasAnyRole("ADMIN")
				.anyRequest().permitAll())
				.csrf(csrfConf -> csrfConf.disable())
				.formLogin(loginConf -> loginConf.loginPage("/user/login")
						.loginProcessingUrl("/doLogin")
						.failureUrl("/user/login?error=T")
						.defaultSuccessUrl("/keep/main", true)
						.usernameParameter("u_email")
						.passwordParameter("u_pwd")
						.permitAll())
				.logout(logoutConf -> logoutConf.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
						.logoutSuccessUrl("/keep/main")
						.invalidateHttpSession(true)
						.deleteCookies("JSESSIONID")
						.permitAll())
				.exceptionHandling(exConf -> exConf.accessDeniedPage("/keep/denied"))
				.oauth2Login(o2l -> o2l.loginPage("/user/login")
						.defaultSuccessUrl("/keep/main",true)
						.failureUrl("/user/login?error=T")
						.userInfoEndpoint(end -> end.userService(pos)));
		
		return http.build();
	}

}
