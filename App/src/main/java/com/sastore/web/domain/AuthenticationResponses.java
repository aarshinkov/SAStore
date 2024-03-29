package com.sastore.web.domain;

/**
 *
 * @author Atanas Yordanov Arshinkov
 */
public enum AuthenticationResponses {

  OK(0, "The authentication is successful"),
  USER_INVALID(1, "The user does not exist"),
  BAD_CREDENTIALS(2, "The user has entered invalid username/password");

  private final Integer response;
  private final String description;

  AuthenticationResponses(Integer response, String description) {
    this.response = response;
    this.description = description;
  }

  public Integer getResponse() {
    return response;
  }

  public String getDescription() {
    return description;
  }
}
