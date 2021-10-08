package com.sastore.web.enums;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
public enum Roles {

  ADMIN("ADMIN"),
  SALES("SALES"),
  USER("USER"),
  PRODUCTS("PRODUCTS"),
  ORDERS("ORDERS");

  private final String role;

  Roles(String role) {
    this.role = role;
  }

  public String getRole() {
    return role;
  }
}
