package com.schooltalk.api.filter;

import static com.schooltalk.api.constants.UrlPath.NOT_REQUIRED_AUTH_URLS;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schooltalk.api.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

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

	private final TokenService tokenService;
	private final UserDetailsService userDetailsService;

	private final AntPathMatcher pathMatcher = new org.springframework.util.AntPathMatcher();


	public JwtAuthenticationFilter(TokenService tokenService, UserDetailsService userDetailsService) {
		this.tokenService = tokenService;
		this.userDetailsService = userDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {
		log.debug("JwtAuthenticationFilter ::: start");

		// 토큰 추출
		String authorizationHeader = request.getHeader(JWT_AUTH_HEADER);
		if (authorizationHeader == null || !authorizationHeader.startsWith(JWT_PREFIX)) {
			log.debug("JwtAuthenticationFilter ::: authorizationHeader not exist : {}", authorizationHeader);
			failAuthentication(response);
			return;
		}

		String jwt = authorizationHeader.replace(JWT_PREFIX, "");
		log.debug("JwtAuthenticationFilter ::: token: [{}]", jwt);

		// 토큰 유효성 검증
		if (!StringUtils.hasText(jwt) || !tokenService.validation(jwt)) {
			log.debug("JwtAuthenticationFilter ::: not a valid token, token: {}", jwt);
			failAuthentication(response);
			return;
		}

		String email = tokenService.getUserEmail(jwt);
		UserDetails userDetails = userDetailsService.loadUserByUsername(email);

		UsernamePasswordAuthenticationToken authentication =
			new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		log.debug("JwtAuthenticationFilter ::: user email: {}", email);

		filterChain.doFilter(request, response);
	}

	private void failAuthentication(HttpServletResponse response) throws IOException {
		SecurityContextHolder.clearContext();

		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType("application/json; charset=UTF-8");

		Map<String, Object> errorBody = new HashMap<>();
		errorBody.put("error", "UNAUTHORIZED");
		errorBody.put("message", "토큰이 없거나 유효하지 않습니다.");
		errorBody.put("status", HttpStatus.UNAUTHORIZED.value());

		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(errorBody);

		response.getWriter().write(json);
		response.getWriter().flush();
	}

	private boolean isNotRequiredAuthUrl(String requestUri) {
		return NOT_REQUIRED_AUTH_URLS != null && NOT_REQUIRED_AUTH_URLS.stream()
			.anyMatch(pattern -> pathMatcher.match(pattern, requestUri));
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		log.debug("shouldNotFilter ::: start uri: {}", request.getRequestURI());
		return isNotRequiredAuthUrl(request.getRequestURI());
	}
}