package com.schooltalk.api.repository;

import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

/**
 * 이 클래스는 Jwt 저장소를 담당합니다.
 *
 * 로그아웃시, 사용했던 JWT를 저장해 다시 접근할 수 없도록 합니다.
 */
@Repository
public class JwtRepository {

	private final RedisTemplate<String, String> redisTemplate;

	private final String STATUS_LOGOUT = "logout";

	public JwtRepository(RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	/**
	 * 로그아웃된 JWT 토큰을 저장합니다.
	 * @param token jwt
	 * @param ttlMillis Repository에 저장할 시간(jwt의 남은 유효시간)
	 */
	public void logout(String token, long ttlMillis) {
		redisTemplate.opsForValue().set(token, STATUS_LOGOUT, ttlMillis, TimeUnit.MILLISECONDS);
	}

	public boolean isLoggedOut(String token) {
		String status = redisTemplate.opsForValue().get(token);
		return STATUS_LOGOUT.equals(status);
	}

	public void delete(String token) {
		redisTemplate.delete(token);
	}
}
