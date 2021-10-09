package com.sastore.web.integration.econt.nomenclatures.requests;

import java.io.Serializable;

/**
 *
 * @author Atanas Yordanov Arshinkov
 */
public class EcontCountryCodeRequest implements Serializable {

  private String countryCode;

  public EcontCountryCodeRequest() {
  }

  public EcontCountryCodeRequest(String countryCode) {
    this.countryCode = countryCode;
  }

  @Override
  public String toString() {
    return "EcontCountryCodeRequest{" + "countryCode=" + countryCode + '}';
  }

  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }
}
