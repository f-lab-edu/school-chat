package com.schooltalk.api.utils;

import static org.assertj.core.api.Assertions.assertThat;

import com.schooltalk.core.entity.User;
import com.schooltalk.core.enums.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

/**
 * 이 클래스는 토큰 Provider의 테스트를 담당합니다.
 */
@SpringBootTest
@TestPropertySource(properties = {
	"jwt.password=testSecretKeyWithAtLeast32Characters",
	"jwt.expiration-time=3600000"
})
class JwtTokenProviderTest {

	@Autowired
	JwtTokenProvider jwtTokenProvider;

	@Test
	void generateToken() {
		// given
		String name = "이름";
		String email = "test@test.com";
		UserRole role = UserRole.STUDENT;
		User user = User.builder().name(name).email(email).role(role).build();

		// when
		String generateToken = jwtTokenProvider.generateToken(user);

		// then
		assertThat(generateToken).isNotBlank();
		assertThat(jwtTokenProvider.validateToken(generateToken)).isTrue();
		assertThat(jwtTokenProvider.getUserEmailFromToken(generateToken)).isEqualTo(email);
	}
}