package com.schooltalk.api.filter;

import com.schooltalk.api.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

/**
 * 이 클래스는 JWT 토큰으로 검증하는 필터를 담당합니다.
 */
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	/**
	 * JWT prefix
	 */
	public static final String JWT_PREFIX = "Bearer ";

	/**
	 * JWT가 저장되는 HTTP header 이름
	 */
	public static final String JWT_AUTH_HEADER = "Authorization";

	/**
	 * JWT 토큰 기반으로 확인하지 않을 URL
	 */
	private final List<String> notRequiredAuthUrls;

	private final TokenService tokenService;
	private final UserDetailsService userDetailsService;

	private final AntPathMatcher pathMatcher = new org.springframework.util.AntPathMatcher();


	public JwtAuthenticationFilter(TokenService tokenService, UserDetailsService userDetailsService, List<String> notRequiredAuthUrls) {
		this.tokenService = tokenService;
		this.notRequiredAuthUrls = notRequiredAuthUrls;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {
		log.debug("JwtAuthenticationFilter ::: start");

		// 토큰 추출
		String authorizationHeader = request.getHeader(JWT_AUTH_HEADER);
		if (authorizationHeader == null|| !authorizationHeader.startsWith(JWT_PREFIX)) {
			log.debug("JwtAuthenticationFilter ::: authorizationHeader not exist : {}", authorizationHeader);
			failAuthentication();
		}

		String jwt = authorizationHeader.replace(JWT_PREFIX, "");
		log.debug("JwtAuthenticationFilter ::: token: [{}]", jwt);

		// 토큰 유효성 검증
		if (!StringUtils.hasText(jwt) || !tokenService.validation(jwt)) {
			log.debug("JwtAuthenticationFilter ::: not a valid token, token: {}", jwt);
			failAuthentication();
		}

		String email = tokenService.getUserEmail(jwt);
		UserDetails userDetails = userDetailsService.loadUserByUsername(email);

		UsernamePasswordAuthenticationToken authentication =
			new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		log.debug("JwtAuthenticationFilter ::: user email: {}", email);

		filterChain.doFilter(request, response);
	}
	private void failAuthentication() {
		SecurityContextHolder.clearContext();
		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "토큰의 정보가 잘못되었습니다.");
	}

	private boolean isNotRequiredAuthUrl(String requestUri) {
		return notRequiredAuthUrls != null && notRequiredAuthUrls.stream()
			.anyMatch(pattern -> pathMatcher.match(pattern, requestUri));
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		log.debug("shouldNotFilter ::: start uri: {}", request.getRequestURI());
		return isNotRequiredAuthUrl(request.getRequestURI());
	}
}