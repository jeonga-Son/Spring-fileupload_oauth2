package com.ll.exam.app10.app.base;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

// @Configuration은 설정파일을 만들기 위한 어노테이션 or Bean을 등록하기 위한 어노테이션이다.
@Configuration
public class BaseConfig {
    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
        return new JPAQueryFactory(entityManager);
    }
}
