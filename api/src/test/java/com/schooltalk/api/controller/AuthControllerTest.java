package com.schooltalk.api.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schooltalk.api.dto.LoginRequest;
import com.schooltalk.api.service.AuthService;
import com.schooltalk.core.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * 이 클래스는 AuthController의 단위 테스트를 담당합니다.
 */
@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

	@Mock
	private AuthService authService;
	@InjectMocks
	private AuthController controller;

	private MockMvc mockMvc;

	private ObjectMapper objectMapper = new ObjectMapper();

	@BeforeEach
	public void initMockMvc() {
		mockMvc = MockMvcBuilders
			.standaloneSetup(controller)
			.build();
	}

	@Test
	@DisplayName("로그인 테스트")
	public void login() throws Exception {
		// given
		LoginRequest request = LoginRequest.builder().email("test@test.com").password("password").role(UserRole.STUDENT).build();


		// stub
		BDDMockito.given(authService.login(BDDMockito.any())).willReturn(BDDMockito.anyString());

		//when
		mockMvc.perform(
				MockMvcRequestBuilders.post("/api/v1/auth/login")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());

		//then
		BDDMockito.verify(authService).login(BDDMockito.any());
	}
}