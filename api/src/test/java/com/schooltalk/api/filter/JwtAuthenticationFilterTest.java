package com.schooltalk.api.filter;

import static com.schooltalk.api.filter.JwtAuthenticationFilter.JWT_AUTH_HEADER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.never;
import static org.mockito.BDDMockito.verify;

import com.schooltalk.api.service.TokenService;
import com.schooltalk.core.enums.UserRole;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 이 클래스는 Jwt 인증 필터의 단위 테스트를 담당합니다.
 */
@ExtendWith(MockitoExtension.class)
class JwtAuthenticationFilterTest {


	private List<String> requiredAuthUrls = List.of("/**");
	@Mock
	private TokenService tokenService;
	@Mock
	private UserDetailsService userDetailsService;
	@Mock
	private FilterChain filterChain;

	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@BeforeEach
	void setUp() {
		jwtAuthenticationFilter = new JwtAuthenticationFilter(tokenService, userDetailsService, requiredAuthUrls);
	}

	@Test
	@DisplayName("JWT 필터 테스트 - 성공")
	void filterValidToken() throws ServletException, IOException {
		// Given
		final String jwt = "Bearer valid token";
		final String email = "test@test.com";
		final String role = UserRole.STUDENT.getAuthority();
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader(JWT_AUTH_HEADER, jwt);
		request.setRequestURI("/api/v1/chat-room");
		UserDetails userDetails = User
			.withUsername(email)
			.password("password")
			.authorities(role)
			.build();

		//stub
		given(tokenService.validation(jwt)).willReturn(true);
		given(tokenService.getUserEmail(jwt)).willReturn(email);
		given(userDetailsService.loadUserByUsername(email)).willReturn(userDetails);

		// When
		jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

		// Then
		verify(filterChain).doFilter(request, response);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		assertThat(authentication).isNotNull();
		assertThat(authentication.getName()).isEqualTo(email);
		assertThat(authentication.isAuthenticated()).isTrue();
		assertThat(authentication.getAuthorities())
			.extracting(GrantedAuthority::getAuthority)
			.contains(role);
	}

	@Test
	@DisplayName("JWT 필터 테스트 - 실패(잘못된 토큰)")
	void filterInvalidToken() throws ServletException, IOException {
		// Given
		String jwt = "Bearer invalid token";
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader(JWT_AUTH_HEADER, jwt);
		request.setRequestURI("/api/v1/chat-room");

		//stub
		given(tokenService.validation(jwt)).willReturn(false);

		// When
		jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

		// Then
		verify(filterChain, never()).doFilter(request, response);
		assertThat(response.getStatus()).isEqualTo(HttpServletResponse.SC_UNAUTHORIZED);
		assertThat(response.getContentAsString()).contains("invalid_token");
	}

	@Test
	@DisplayName("JWT 필터 테스트 - 실패(토큰 없음, null)")
	void filterNullToken() throws ServletException, IOException {
		// Give
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setRequestURI("/api/v1/chat-room");

		// When
		jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

		// Then
		verify(filterChain, never()).doFilter(request, response);
		assertThat(response.getStatus()).isEqualTo(HttpServletResponse.SC_UNAUTHORIZED);
		assertThat(response.getContentAsString()).contains("invalid_token");
	}

	@Test
	@DisplayName("JWT 필터 테스트 - 실패(토큰 없음 - 공백)")
	void filterEmptyToken() throws ServletException, IOException {
		// Given
		String jwt = "  ";
		MockHttpServletResponse response = new MockHttpServletResponse();
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader(JWT_AUTH_HEADER, jwt);
		request.setRequestURI("/api/v1/chat-room");

		// When
		jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

		// Then
		verify(filterChain, never()).doFilter(request, response);
		assertThat(response.getStatus()).isEqualTo(HttpServletResponse.SC_UNAUTHORIZED);
		assertThat(response.getContentAsString()).contains("invalid_token");
	}
}