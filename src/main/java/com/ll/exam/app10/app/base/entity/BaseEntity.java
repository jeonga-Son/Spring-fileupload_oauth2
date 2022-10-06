package com.ll.exam.app10.app.base.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@SuperBuilder
// @MappedSuperclass는 공통 매핑 정보가 필요할 때 사용한다.
// 똑같고 반복되는 속성이 존재할 때 공통 속성을 갖는 Entity를 상속받고 싶을 때 사용할 수 있다.
// JPA Entity 클래스들이 BaseEntity 상속할 경우 이 클래스의 필드도 컬럼으로 인식하게 된다.
// 상속용이다.
@MappedSuperclass
// @NoArgsConstructor(access = PROTECTED)는 기본 생성자의 접근 제어를 PROTECTED로 설정해놓는다.
// 따라서 무분별한 객체 생성에 대해 한번 더 체크할 수 있는 수단이 된다.
@NoArgsConstructor(access = PROTECTED)
//@EntityListeners(AuditingEntityListener.class)는 BaseEntity 클래스에 Auditing 기능을 포함시킨다
@EntityListeners(AuditingEntityListener.class)
@ToString
public class BaseEntity {
    @Id
    // @GeneratedValue(strategy = IDENTITY)는 기본 키 생성을 데이터베이스에 위임.
    // 즉, id 값을 null로 하면 DB가 알아서 AUTO_INCREMENT 해준다.
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @CreatedDate
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime modifyDate;

    public BaseEntity(long id) {
        this.id = id;
    }
}
