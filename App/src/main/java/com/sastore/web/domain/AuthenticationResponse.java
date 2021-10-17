package com.sastore.web.domain;

/**
 *
 * @author Atanas Yordanov Arshinkov
 */
public class AuthenticationResponse {

  private Integer code;
  private String description;
  private Boolean hasErrors;

  @Override
  public String toString() {
    return "AuthenticationResponse{" + "code=" + code + ", description=" + description + ", hasErrors=" + hasErrors + '}';
  }

  public void setResponse(AuthenticationResponses response) {
    this.code = response.getResponse();
    this.description = response.getDescription();
    this.hasErrors = !response.getResponse().equals(AuthenticationResponses.OK.getResponse());
  }

  public Integer getCode() {
    return code;
  }

  public String getDescription() {
    return description;
  }

  public Boolean hasErrors() {
    return hasErrors;
  }
}
