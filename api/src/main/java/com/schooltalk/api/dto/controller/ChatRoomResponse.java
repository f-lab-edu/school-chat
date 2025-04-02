package com.schooltalk.api.dto.controller;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 이 클래스는 채팅방 정보의 응답 DTO를 담당합니다.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomResponse {

	/** 채팅방 id */
	private String id;

	/** 채팅방 이름 */
	private String name;

	/** 채팅방 유저 목록 */
	private List<String> users;

	/** 채팅방 마지막 메시지 */
	private String lastMessage;

	/** 채팅방 마지막 대화 시간 */
	private LocalDateTime lastMessageTime;
}
