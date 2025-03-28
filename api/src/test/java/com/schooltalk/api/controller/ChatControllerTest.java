package com.schooltalk.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.schooltalk.api.filter.JwtAuthenticationFilter;
import com.schooltalk.api.service.TokenService;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * 이 클래스는 채팅방에 대한 컨트롤러의 단위 테스트를 담당합니다.
 */
@ExtendWith(MockitoExtension.class)
class ChatControllerTest {

	private List<String> requiredAuthUrls = Arrays.asList("/api/v1/chat-room", "/api/v1/chat-room/*");
	@Mock
	private TokenService tokenService;
	@InjectMocks
	private ChatController controller;

	private MockMvc mockMvc;

	@BeforeEach
	public void initMockMvc() {
		mockMvc = MockMvcBuilders
			.standaloneSetup(controller)
			.addFilter(new JwtAuthenticationFilter(tokenService, requiredAuthUrls))
			.build();

	}

	@Test
	@DisplayName("JWT 필터가 동작")
	void shouldFilterProtectedUrl() throws Exception {
		// Given
		String doFilterUrl = "/api/v1/chat-room";

		// When & Then
		mockMvc.perform(get(doFilterUrl))
			.andExpect(status().isUnauthorized());
	}

}