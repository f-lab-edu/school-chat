package com.schooltalk.api.service;

import com.schooltalk.api.repository.JwtRepository;
import com.schooltalk.api.utils.JwtTokenProvider;
import com.schooltalk.core.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 이 클래스는 토큰 서비스의 JWT 방식으로 한 구현을 담당합니다.
 */
@Slf4j
@Service
public class TokenServiceImpl implements TokenService {

	private final JwtTokenProvider jwtTokenProvider;
	private final JwtRepository jwtRepository;

	public TokenServiceImpl(JwtTokenProvider jwtTokenProvider, JwtRepository jwtRepository) {
		this.jwtTokenProvider = jwtTokenProvider;
		this.jwtRepository = jwtRepository;
	}

	@Override
	public String generateToken(User userInfo) {
		return jwtTokenProvider.generateToken(userInfo);
	}

	@Override
	public void logout(String token) {
		long expiresInMillis = jwtTokenProvider.getExpiresInMillis(token);
		jwtRepository.logout(token, expiresInMillis);
	}

	@Override
	public boolean validation(String token) {
		return jwtTokenProvider.validateToken(token) && !jwtRepository.isLoggedOut(token);
	}

	@Override
	public String getUserEmail(String token) {
		return jwtTokenProvider.getUserEmailFromToken(token);
	}
}
