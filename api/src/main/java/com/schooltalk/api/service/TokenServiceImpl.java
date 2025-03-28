package com.schooltalk.api.service;

import com.schooltalk.api.repository.JwtRepository;
import com.schooltalk.api.utils.JwtTokenProvider;
import com.schooltalk.core.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 이 클래스는 토큰 서비스의 구현을 담당합니다.
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
	public String generateToken(User userInfo, boolean addPrefix) {
		String token = jwtTokenProvider.generateToken(userInfo);
		return addPrefix ? JWT_PREFIX + token : token;
	}

	@Override
	public void logout(String token) {
		if (hasPrefix(token)) {
			token = token.substring(JWT_PREFIX.length());
		}
		jwtRepository.logout(token);
	}

	@Override
	public boolean validation(String token) {
		if (hasPrefix(token)) {
			token = token.substring(JWT_PREFIX.length());
		}

		return jwtTokenProvider.validateToken(token) && !jwtRepository.isLoggedOut(token);
	}

	@Override
	public String getUserEmail(String token) {
		if (hasPrefix(token)) {
			token = token.substring(JWT_PREFIX.length());
		}
		return jwtTokenProvider.getUserEmailFromToken(token);
	}

	private boolean hasPrefix(String token) {;
		return token.startsWith(JWT_PREFIX);
	}
}
