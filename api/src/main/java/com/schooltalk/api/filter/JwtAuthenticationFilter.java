package com.schooltalk.api.filter;

import com.schooltalk.api.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 이 클래스는 JWT 토큰으로 검증하는 필터를 담당합니다.
 */
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	/**
	 * JWT 토큰 기반으로 확인해야하는 URL
	 */
	private final List<String> requiredAuthUrls;

	private final TokenService tokenService;

	private final AntPathMatcher pathMatcher = new org.springframework.util.AntPathMatcher();


	public JwtAuthenticationFilter(TokenService tokenService, List<String> requiredAuthUrls) {
		this.tokenService = tokenService;
		this.requiredAuthUrls = requiredAuthUrls;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {
		log.debug("JwtAuthenticationFilter ::: start");

		// 토큰 추출
		String jwt = request.getHeader(TokenService.JWT_AUTH_HEADER);
		log.debug("JwtAuthenticationFilter ::: token: [{}]", jwt);

		// 토큰 유효성 검증
		if (!StringUtils.hasText(jwt) || !tokenService.validation(jwt)) {
			log.error("JwtAuthenticationFilter ::: not a valid token, token: {}", jwt);
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setContentType("application/json");
			response.getWriter().write("{\"error\":\"invalid_token\"}");
			return;
		}

		String email = tokenService.getUserEmail(jwt);
		request.setAttribute("email", email);
		log.debug("JwtAuthenticationFilter ::: user email: {}", email);

		filterChain.doFilter(request, response);
	}

	private boolean isRequiredAuthUrl(String requestUri) {
		return requiredAuthUrls != null && requiredAuthUrls.stream()
			.anyMatch(pattern -> pathMatcher.match(pattern, requestUri));
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		log.debug("shouldNotFilter ::: start uri: {}", request.getRequestURI());
		return !isRequiredAuthUrl(request.getRequestURI());
	}
}
