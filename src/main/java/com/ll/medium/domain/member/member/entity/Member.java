package com.ll.medium.domain.member.member.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include // 값 비교시 id만 비교
    private Long id;
    private String username;
    private String password;

    public boolean isAdmin() {
        return username.equals(Role.ADMIN);
    }

    // 권한 부여
    public List<SimpleGrantedAuthority> getAuthorities() {
        if (isAdmin()) {
            return List.of(new SimpleGrantedAuthority(Role.ADMIN.value()),
                    new SimpleGrantedAuthority(Role.MEMBER.value()));
        }

        return List.of(new SimpleGrantedAuthority(Role.MEMBER.value()));
    }
}
