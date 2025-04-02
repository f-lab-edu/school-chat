package com.schooltalk.core.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 이 클래스는 JPA 설정을 담당합니다.
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.schooltalk.core.repository")
@EntityScan(basePackages = "com.schooltalk.core.entity")
public class JpaConfig {

}
