package com.schooltalk.api.service;

import com.schooltalk.api.dto.controller.LoginRequest;
import com.schooltalk.core.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 이 클래스는 인증 관련 서비스의 구현을 담당합니다.
 */
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

	private final TokenService tokenService;

	public AuthServiceImpl(TokenService tokenService) {
		this.tokenService = tokenService;
	}

	@Override
	public String login(LoginRequest request) {
		// todo 인증과정 구현


		// JWT 토큰발급
		User user = request.toUser();
		return tokenService.generateToken(user, true);
	}

	@Override
	public boolean verify(LoginRequest request) {
		// todo 구현
		return false;
	}

	@Override
	public String refresh(LoginRequest request) {
		// todo 구현
		return "";
	}

	@Override
	public void logout(String token) {
		tokenService.logout(token);
	}
}