package com.schooltalk.core.enums;

/**
 * 이 클래스는 유저의 역할 정의를 담당합니다.
 */
public enum UserRole {
	/**
	 * 학생
	 */
	STUDENT,


	/**
	 * 선생님
	 */
	TEACHER,


	/**
	 * 학부모
	 */
	PARENT;

	public String getAuthority() {
		return "ROLE_" + this.name();
	}
}
