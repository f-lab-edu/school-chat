package com.schooltalk.api.dto.controller;

import com.schooltalk.core.entity.User;
import com.schooltalk.core.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 이 클래스는 로그인 요청 정보를 담당합니다.
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
public class LoginRequest {

	/**
	 * 역할
	 */
	private UserRole role;

	/**
	 * 이메일
	 */
	private String email;

	/**
	 * 비밀번호
	 */
	private String password;

	/**
	 * Entity로 변환
	 * @return User entity
	 */
	public User toUser() {
		return User.builder().email(email).password(password).role(role).build();
	}
}
