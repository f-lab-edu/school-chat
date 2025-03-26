package com.schooltalk.api.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 이 클래스는 화면 컨트롤러를 담당합니다.
 */
@Controller
@RequestMapping
public class ViewController {
	/**
	 * 로그인 페이지 요청
	 * @return 로그인 페이지
	 */
	@GetMapping("/")
	public String homePage() {
		return "index";
	}

	/**
	 * 로그인 페이지 요청
	 * @return 로그인 페이지
	 */
	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}
}
