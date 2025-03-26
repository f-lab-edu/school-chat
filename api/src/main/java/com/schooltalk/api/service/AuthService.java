package com.schooltalk.api.service;

import com.schooltalk.api.dto.LoginRequest;

/**
 * 이 클래스는 인증 관련 서비스를 담당합니다.
 */
public interface AuthService {
	/**
	 * 로그인 처리
	 * @param request 로그인 정보
	 * @return JTW 토큰
	 */
	String login(LoginRequest request);

	/**
	 * JWT 유효성
	 * @param request JWT
	 * @return 토큰의 유효성
	 */
	boolean verify(LoginRequest request);
	/**
	 * JWT 리프레시 토큰을 이용해 JWT 재발급
	 * @param request 리프레시 토큰
	 * @return JWT
	 */
	String refresh(LoginRequest request);


	/**
	 * 로그아웃 처리
	 *
	 * @param token JTW 토큰
	 */
	void logout(String token);
}
