package com.ll.medium.domain.member.member.entity;

import com.ll.medium.global.jpa.baseEntity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Entity
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@ToString(callSuper = true)
public class Member extends BaseEntity {
    private String username;
    private String password;

    public boolean isAdmin() {
        return username.equals(Role.ADMIN);
    }

    // 권한 부여
    @SuppressWarnings("JpaAttributeTypeInspection") // 무시해도 되는 오류를 끌 수 있다.
    public List<SimpleGrantedAuthority> getAuthorities() {
        if (isAdmin()) {
            return List.of(new SimpleGrantedAuthority(Role.ADMIN.value()),
                    new SimpleGrantedAuthority(Role.MEMBER.value()));
        }

        return List.of(new SimpleGrantedAuthority(Role.MEMBER.value()));
    }
}
