package com.ll.medium.domain.member.member.entity;

public enum Role {
    MEMBER("ROLE_MEMBER"),
    ADMIN("ROLE_ADMIN"),
    PAID("ROLE_PAID");

    String role;

    Role(String role) {
        this.role = role;
    }

    public String value() {
        return role;
    }
}
