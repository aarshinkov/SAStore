package com.sastore.web.utils;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
public enum Methods {
  GET("GET"),
  POST("POST"),
  PUT("PUT"),
  DELETE("DELETE");

  private String method;

  Methods(String method) {
    this.method = method;
  }

  /**
   * @return the method name
   */
  public String getMethod() {
    return method.toUpperCase();
  }

  /**
   * @param method the method name to be set
   */
  public void setMethod(String method) {
    this.method = method.toUpperCase();
  }
}
