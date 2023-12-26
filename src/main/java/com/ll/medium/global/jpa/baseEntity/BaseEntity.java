package com.ll.medium.global.jpa.baseEntity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass // 부모 클래스로 사용되는 것을 나타낸다, 매핑 정보 상속
@EntityListeners(AuditingEntityListener.class) // @CreatedDate, @LastModifiedDate를 사용하기 위해 필요
@SuperBuilder // 부모 클래스인 BaseEntity에서도 빌더 패턴을 사용할 수 있다.
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 매개변수가 없는 protected 접근 제한자를 가지는 기본 생성자를 자동으로 생성
@Getter // 필드에 대한 getter 메서드가 자동으로 생성
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // equals()와 hashCode() 메서드를 자동으로 생성
@ToString // toString() 메서드를 자동으로 생성
public class BaseEntity { // 모든 엔티티가 가져야할 공통 속성들을 모아두는 클래스 BaseEntity 도입
    @Id // 엔티티의 식별자(primary key)로 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 엔티티의 식별자 값을 자동으로 생성
    @EqualsAndHashCode.Include // 이 어노테이션이 적용된 필드는 equals()와 hashCode() 메서드에서 동등성 비교에 사용
    private Long id;
    @CreatedDate // 해당 필드의 값은 엔티티가 생성될 때 자동으로 설정 (JPA Auditing)
    private LocalDateTime createdDate;
    @LastModifiedDate
    @Setter
    private LocalDateTime modifiedDate;
}
