package com.schooltalk.api.service;

import com.schooltalk.core.entity.User;

/**
 * 이 클래스는 토큰 서비스를 담당합니다.
 */
public interface TokenService {

	/**
	 * JWT prefix
	 */
	String JWT_PREFIX = "Bearer ";

	/**
	 * JWT가 저장되는 HTTP header 이름
	 */
	String JWT_AUTH_HEADER = "Authorization";


	/**
	 * 토큰 생성
	 *
	 * @param userInfo  유저 정보
	 * @param addPrefix JWT_PREFIX를 붙여서 반환할지
	 * @return JWT 토큰
	 */
	String generateToken(User userInfo, boolean addPrefix);

	/**
	 * 로그아웃 처리
	 *
	 * @param token jwt
	 */
	void logout(String token);


	/**
	 * 토큰 유효성 검증
	 * @param token jwt
	 * @return 결과
	 */
	boolean validation(String token);
}
