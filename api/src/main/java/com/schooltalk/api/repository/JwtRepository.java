package com.schooltalk.api.repository;

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

	public void logout(String token) {
		redisTemplate.opsForValue().set(token, STATUS_LOGOUT);
	}

	public boolean isLoggedOut(String token) {
		String status = redisTemplate.opsForValue().get(token);
		return STATUS_LOGOUT.equals(status);
	}

	public void delete(String token) {
		redisTemplate.delete(token);
	}
}
