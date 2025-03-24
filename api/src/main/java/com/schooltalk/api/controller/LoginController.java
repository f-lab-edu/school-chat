package com.schooltalk.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 이 클래스는 로그인 컨트롤러을 담당합니다.
 */
@Controller
public class LoginController {

	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}
}
