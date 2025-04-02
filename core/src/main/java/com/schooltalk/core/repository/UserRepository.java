package com.schooltalk.core.repository;

import com.schooltalk.core.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 이 클래스는 유저 레포지토리를 담당합니다.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
}
