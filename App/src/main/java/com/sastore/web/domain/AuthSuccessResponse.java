package com.sastore.web.domain;

import java.io.Serializable;

/**
 *
 * @author Atanas Yordanov Arshinkov
 */
public class AuthSuccessResponse implements Serializable {

  private String redirectUrl;

  public String getRedirectUrl() {
    return redirectUrl;
  }

  public void setRedirectUrl(String redirectUrl) {
    this.redirectUrl = redirectUrl;
  }
}
