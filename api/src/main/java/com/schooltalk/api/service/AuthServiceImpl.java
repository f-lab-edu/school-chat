package com.schooltalk.api.service;

import com.schooltalk.api.dto.controller.LoginRequest;
import com.schooltalk.core.entity.User;
import com.schooltalk.core.repository.UserRepository;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * 이 클래스는 인증 관련 서비스의 구현을 담당합니다.
 */
@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

	private final TokenService tokenService;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public AuthServiceImpl(TokenService tokenService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.tokenService = tokenService;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public String login(User user) {
		Optional<User> userByEmail = userRepository.findByEmail(user.getEmail());
		if (userByEmail.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "해당 회원정보가 존재하지 않습니다.");
		}

		User findUser = userByEmail.get();

		if (findUser.getRole() != user.getRole()) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "회원정보가 다릅니다.");
		}

		if (!passwordEncoder.matches(user.getPassword(), findUser.getPasswordEnc())) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "회원정보가 다릅니다.");
		}

		// JWT 토큰발급
		return tokenService.generateToken(user);
	}

	@Override
	public boolean verify(LoginRequest request) {
		// todo 구현
		return false;
	}

	@Override
	public String refresh(LoginRequest request) {
		// todo 구현
		return "";
	}

	@Override
	public void logout(String token) {
		tokenService.logout(token);
	}
}