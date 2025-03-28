package com.schooltalk.api.controller;

import com.schooltalk.api.dto.ChatRoomResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 이 클래스는 채팅방에 대한 컨트롤러을 담당합니다.
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/chat-room")
public class ChatController {

	@GetMapping
	public ResponseEntity<List<ChatRoomResponse>> getChatRooms() {
		// todo 실제 채팅방 조회 로직으로 변경
		List<ChatRoomResponse> responses = new ArrayList<>();

		log.info("getChatRooms ::: responses.size = {}", responses.size());
		return ResponseEntity.ok(responses);
	}

}
