package com.ll.exam.app10;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
// Spring Data JPA 에서 JPA 를 사용하기 위해서는 SpringBoot 설정 클래스에 @EnableJpaAuditing 을 적어줘야한다.
// @EntityListeners(AuditingEntityListener.class)가 실행되려면 @EnableJpaAuditing이 되어있어야 함.
@EnableJpaAuditing
public class App10Application {

	public static void main(String[] args) {
		SpringApplication.run(App10Application.class, args);
	}

}
