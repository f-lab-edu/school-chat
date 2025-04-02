package com.schooltalk.core.entity;

import com.schooltalk.core.enums.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 이 클래스는 User 엔티티를 담당합니다.
 */
@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
@Builder
@AllArgsConstructor
public class User {

	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 유저 이름
	 */
	@Column(name = "username", nullable = false, length = 20)
	private String name;

	/**
	 * 유저 역할
	 */
	@Column(name = "role", nullable = false, length = 20)
	@Enumerated(EnumType.STRING)
	private UserRole role;

	/**
	 * 유저 이메일
	 */
	@Column(name = "email", nullable = false, length = 100, unique = true)
	private String email;

	/**
	 * 유저 비밀번호
	 */
	@Column(name = "password_enc", nullable = false, length = 200)
	private String passwordEnc;

	/**
	 * 생성 일시
	 */
	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	/**
	 * 추가한 사람(=선생님)
	 */
	@Column(name = "created_by")
	private Long createdBy;

	/**
	 * 인증 키
	 */
	@Column(name = "activation_key", length = 100)
	private String activationKey;

	/**
	 * 마지막으로 수정된 일시
	 */
	@Column(name = "updated_at", nullable = false)
	private LocalDateTime updatedAt;

}
