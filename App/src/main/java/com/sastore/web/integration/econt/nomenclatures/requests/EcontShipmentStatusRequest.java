package com.sastore.web.integration.econt.nomenclatures.requests;

import java.io.*;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
public class EcontShipmentStatusRequest implements Serializable {

  private String[] shipmentNumbers;

  @Override
  public String toString() {
    return "EcontShipmentStatusRequest{" + "shipmentNumbers=" + shipmentNumbers + '}';
  }

  public String[] getShipmentNumbers() {
    return shipmentNumbers;
  }

  public void setShipmentNumbers(String[] shipmentNumbers) {
    this.shipmentNumbers = shipmentNumbers;
  }
}
