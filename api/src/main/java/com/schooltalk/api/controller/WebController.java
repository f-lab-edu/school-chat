package com.schooltalk.api.controller;


//import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 이 클래스는 WEB 컨트롤러를 담당합니다.
 *
 * <p>WEB 화면을 위한 컨트롤러 입니다.
 *
 * @since version 0.0.1
 */
@Controller
public class WebController {
	@GetMapping("/")
	public String home() {
		return "login";
	}


	@GetMapping("/login")
	public String login() {
		return "login";
	}
}
