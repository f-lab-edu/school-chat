package com.schooltalk.api.service;

import com.schooltalk.core.entity.User;
import com.schooltalk.core.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 이 클래스는 Security의 User Detail 구현을 담당합니다.
 *
 * <p> <code>UserDetailsService</code>: Spring Security에서 로그인 시 사용자 정보를 불러오는 인터페이스
 * <p> 로그인 요청시 사용자 정보를 조회해 인증하는 작업을 담당
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// 1. 사용자 조회
		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		// 2. UserDetails 반환
		return org.springframework.security.core.userdetails.User
			.withUsername(user.getEmail())
			.password(user.getPasswordEnc())
			.authorities(user.getRole().getAuthority())
			.build();
	}
}
