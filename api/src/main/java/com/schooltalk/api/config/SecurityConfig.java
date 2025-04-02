package com.schooltalk.api.config;

import com.schooltalk.api.filter.JwtAuthenticationFilter;
import com.schooltalk.api.service.TokenService;
import java.util.Arrays;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 이 클래스는 security 설정을 담당합니다.
 */
@Configuration
public class SecurityConfig implements WebMvcConfigurer {

	/**
	 * 인증 인가가 필요하지 않은 URL
	 */
	private final List<String> notRequiredAuthUrls = Arrays.asList(
		"/api/v1/auth/login"
	);

	private final TokenService tokenService;
	private final UserDetailsService userDetailsService;

	public SecurityConfig(TokenService tokenService, UserDetailsService userDetailsService) {
		this.tokenService = tokenService;
		this.userDetailsService = userDetailsService;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter(tokenService, userDetailsService, notRequiredAuthUrls);
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((authorize) -> {
				notRequiredAuthUrls.forEach(url -> authorize.requestMatchers(url).permitAll());
				authorize.anyRequest().authenticated();
				}
			)
			.csrf((csrf) -> {
				notRequiredAuthUrls.forEach(csrf::ignoringRequestMatchers);
			})
			.httpBasic(AbstractHttpConfigurer::disable) // HTTP Basic 인증 비활성화
			.formLogin(AbstractHttpConfigurer::disable) // 폼 로그인 사용하지 않음
			.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 사용하지 않음
			.exceptionHandling((exceptions) -> exceptions
				.authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
				.accessDeniedHandler(new BearerTokenAccessDeniedHandler())
			).addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class); // jwt 필터를 추가
		return http.build();
	}

}
