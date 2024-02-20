package edu.ssafy.enjoytrip.dto.user;

import org.apache.ibatis.type.MappedTypes;

public enum Role {
    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String value() {
        return role;
    }

}
