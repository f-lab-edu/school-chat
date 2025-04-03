package com.schooltalk.api.constants;

import java.util.Arrays;
import java.util.List;

/**
 * 이 클래스는 URL Path 정의을 담당합니다.
 */
public final class UrlPath {

	public static final String REST_PREFIX = "/api";
	public static final String REST_VERSION = "/v1";

	public static final class Auth {

		public static final String ROOT = REST_PREFIX + REST_VERSION + "/auth";
		public static final String LOGIN = "/login";
		public static final String LOGOUT = "/logout";

	}
	public static final class ChatRoom {
		public static final String ROOT = REST_PREFIX + REST_VERSION + "/chat-rooms";

	}

	/**
	 * 인증 인가가 필요하지 않은 URL
	 */
	public static final List<String> NOT_REQUIRED_AUTH_URLS = Arrays.asList(
		Auth.ROOT+ Auth.LOGIN,
		Auth.ROOT+ Auth.LOGOUT
	);

}
