package com.schooltalk.api.service;

import com.schooltalk.api.utils.JwtTokenProvider;
import com.schooltalk.core.entity.User;
import org.springframework.stereotype.Service;

/**
 * 이 클래스는 토큰 서비스의 구현을 담당합니다.
 */
@Service
public class TokenServiceImpl implements TokenService {

	private final JwtTokenProvider jwtTokenProvider;

	public TokenServiceImpl(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
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
	}

	@Override
	public boolean validation(String token) {
		if (hasPrefix(token)) {
			token = token.substring(JWT_PREFIX.length());
		}

		return jwtTokenProvider.validateToken(token);
	}

	private boolean hasPrefix(String token) {
		return token.startsWith(JWT_PREFIX);
	}
}
