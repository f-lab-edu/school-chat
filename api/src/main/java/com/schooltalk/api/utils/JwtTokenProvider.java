package com.schooltalk.api.utils;

import com.schooltalk.core.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 이 클래스는 JWT 관련된 유틸 제공을 담당합니다.
 */
@Slf4j
@Component
public class JwtTokenProvider {

	/**
	 * 시크릿 키
	 */
	private final SecretKey SECRET_KEY;
	/**
	 * JWT 유효 시간,  단위: 밀리초(ms)
	 */
	private final long EXPIRATION_TIME_MILLIS;

	public JwtTokenProvider(@Value("${jwt.password}") String password, @Value("${jwt.expiration-millis}") long expirationTime) {
		this.SECRET_KEY = Keys.hmacShaKeyFor(password.getBytes(StandardCharsets.UTF_8));
		this.EXPIRATION_TIME_MILLIS = expirationTime;
	}

	/**
	 * 생성
	 *
	 * @param userInfo 유저 정보
	 * @return JWT 토큰
	 */
	public String generateToken(User userInfo) {
		Instant now = Instant.now();
		Instant expirationTime = now.plusMillis(EXPIRATION_TIME_MILLIS);

		return Jwts.builder()
			.subject(userInfo.getEmail())
			.claim("email", userInfo.getEmail())
			.claim("role", userInfo.getRole())
			.claim("username", userInfo.getName())
			.issuedAt(Date.from(now))
			.expiration(Date.from(expirationTime))
			.signWith(SECRET_KEY)
			.compact();
	}

	/**
	 * 사용자 이메일 추출
	 *
	 * @param token JWT 토큰
	 * @return 사용자 이메일
	 */
	public String getUserEmailFromToken(String token) {
		Optional<Claims> payload = getPayload(token);
		return payload.map(Claims::getSubject).orElse(null);
	}

	/**
	 * 만료시간까지 남은 시간, 단위 ms
	 *
	 * @param token JWT 토큰
	 * @return 만료까지 남은 시간
	 */
	public long getExpiresInMillis(String token) {
		Optional<Claims> payload = getPayload(token);

		Date expirationTime = payload.map(Claims::getExpiration).orElse(null);
		if (expirationTime == null) {
			log.error("No expirationTime time found for token {}", token);
			throw new IllegalArgumentException("expirationTime must not be null");
		}
		return Duration.between(Instant.now(), expirationTime.toInstant()).toMillis();
	}

	/**
	 * payload 추출
	 *
	 * @param token JWT 토큰
	 * @return Payload
	 */
	private Optional<Claims> getPayload(String token) {
		try {
			return Optional.of(Jwts.parser()
				.verifyWith(SECRET_KEY)
				.build()
				.parseSignedClaims(token)// 서명 검증
				.getPayload());
		} catch (Exception e) {
			log.error("Error while parsing token. message = {}", e.getMessage());
			return Optional.empty();
		}
	}

	/**
	 * 유효성 검사
	 *
	 * @param token JWT 토큰
	 * @return 유효한지 결과
	 */
	public boolean validateToken(String token) {
		Optional<Claims> payload = getPayload(token);
		return payload.filter(value -> !value.getExpiration().before(Date.from(Instant.now()))).isPresent();
	}
}
