package com.schooltalk.api.config;

import com.schooltalk.api.filter.JwtAuthenticationFilter;
import com.schooltalk.api.service.TokenService;
import java.util.Arrays;
import java.util.List;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 이 클래스는 security 설정을 담당합니다.
 */
@Configuration
public class SecurityConfig implements WebMvcConfigurer {

	private final TokenService tokenService;

	public SecurityConfig(TokenService tokenService) {
		this.tokenService = tokenService;
	}

	@Bean
	public FilterRegistrationBean<JwtAuthenticationFilter> jwtFilter() {
		List<String> requiredAuthUrls = Arrays.asList("/api/v1/chat-room", "/api/v1/chat-room/**");

		FilterRegistrationBean<JwtAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new JwtAuthenticationFilter(tokenService, requiredAuthUrls));
		registrationBean.addUrlPatterns("*"); //filter 동작 url todo security를 이용해 특정 url(로그인 등) 제외 후 전부로 등록
		registrationBean.setOrder(1);
		return registrationBean;
	}
}
