package com.schooltalk.api.controller;

import com.schooltalk.api.dto.LoginRequest;
import com.schooltalk.api.dto.LoginResponse;
import com.schooltalk.api.service.AuthService;
import com.schooltalk.api.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 이 클래스는 인증의 컨트롤러를 담당합니다.
 */
@RestController
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	/**
	 * 로그인 요청(JWT 발급)
	 *
	 * @param loginRequest 유저 역할
	 * @return JWT 발급
	 */
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
		log.info("Login request: {}", loginRequest);
		String jwt = authService.login(loginRequest);
		log.info("JWT token: {}", jwt);
		return ResponseEntity.ok(new LoginResponse(jwt));
	}

	/**
	 * 로그아웃
	 *
	 * @param token JWT
	 */
	@PostMapping("/logout")
	@ResponseBody
	public ResponseEntity<?> logout(@RequestHeader(TokenService.JWT_AUTH_HEADER) String token) {
		log.info("Logout request: {}", token);
		authService.logout(token);
		return ResponseEntity.ok(null);
	}
}
