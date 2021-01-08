package com.sastore.web.enums;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
public enum Roles {
    ADMIN("ADMIN"),
    USER("USER"),
    SALES("SALES");

    private final String role;

    Roles(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
