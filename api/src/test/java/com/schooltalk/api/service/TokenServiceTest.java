package com.schooltalk.api.service;


import static org.assertj.core.api.Assertions.assertThat;

import com.schooltalk.api.repository.JwtRepository;
import com.schooltalk.api.utils.JwtTokenProvider;
import com.schooltalk.core.entity.User;
import com.schooltalk.core.enums.UserRole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * 이 클래스는 토큰 서비스의 테스트을 담당합니다.
 */
@ExtendWith(MockitoExtension.class)
class TokenServiceTest {


	@Mock
	JwtTokenProvider jwtTokenProvider;
	@Mock
	JwtRepository jwtRepository;
	@InjectMocks
	TokenServiceImpl tokenService;

	@Test
	void generateToken() {
		// given
		String name = "이름";
		String email = "test@test.com";
		UserRole role = UserRole.STUDENT;
		User user = User.builder().name(name).email(email).role(role).build();
		String token = "token";

		// stub
		BDDMockito.given(jwtTokenProvider.generateToken(user)).willReturn(token);

		// when
		String generateToken = tokenService.generateToken(user, false);

		// then
		assertThat(generateToken).isEqualTo(token);
	}
}