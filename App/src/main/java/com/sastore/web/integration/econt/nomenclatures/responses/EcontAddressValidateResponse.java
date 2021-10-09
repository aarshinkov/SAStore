package com.sastore.web.integration.econt.nomenclatures.responses;

import com.sastore.web.integration.econt.nomenclatures.EcontAddress;
import java.io.Serializable;

/**
 *
 * @author Atanas Yordanov Arshinkov
 */
public class EcontAddressValidateResponse implements Serializable {

  private EcontAddress address;
  private String validationStatus;

  public EcontAddressValidateResponse() {
  }

  public EcontAddressValidateResponse(EcontAddress address, String validationStatus) {
    this.address = address;
    this.validationStatus = validationStatus;
  }

  @Override
  public String toString() {
    return "EcontAddressValidateResponse{" + "address=" + address + ", validationStatus=" + validationStatus + '}';
  }

  public EcontAddress getAddress() {
    return address;
  }

  public void setAddress(EcontAddress address) {
    this.address = address;
  }

  public String getValidationStatus() {
    return validationStatus;
  }

  public void setValidationStatus(String validationStatus) {
    this.validationStatus = validationStatus;
  }
}
