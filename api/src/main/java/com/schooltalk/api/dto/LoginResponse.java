package com.schooltalk.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 이 클래스는 로그인 응답을 담당합니다.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LoginResponse {
	private String token;
}
