package com.sastore.web.integration.econt.nomenclatures;

import java.io.Serializable;

/**
 *
 * @author Atanas Yordanov Arshinkov
 */
public class EcontAddress implements Serializable {

  private Integer id;
  private EcontCity city;
  private String fullAddress;
  private String quarter;
  private String street;
  private String num;
  private String other;
  private EcontGeoLocation location;
  private String zip;

  public EcontAddress() {
  }

  public EcontAddress(Integer id, EcontCity city, String fullAddress, String quarter, String street, String num, String other, EcontGeoLocation location, String zip) {
    this.id = id;
    this.city = city;
    this.fullAddress = fullAddress;
    this.quarter = quarter;
    this.street = street;
    this.num = num;
    this.other = other;
    this.location = location;
    this.zip = zip;
  }

  @Override
  public String toString() {
    return "EcontAddress{" + "id=" + id + ", city=" + city + ", fullAddress=" + fullAddress + ", quarter=" + quarter + ", street=" + street + ", num=" + num + ", other=" + other + ", location=" + location + ", zip=" + zip + '}';
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public EcontCity getCity() {
    return city;
  }

  public void setCity(EcontCity city) {
    this.city = city;
  }

  public String getFullAddress() {
    return fullAddress;
  }

  public void setFullAddress(String fullAddress) {
    this.fullAddress = fullAddress;
  }

  public String getQuarter() {
    return quarter;
  }

  public void setQuarter(String quarter) {
    this.quarter = quarter;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getNum() {
    return num;
  }

  public void setNum(String num) {
    this.num = num;
  }

  public String getOther() {
    return other;
  }

  public void setOther(String other) {
    this.other = other;
  }

  public EcontGeoLocation getLocation() {
    return location;
  }

  public void setLocation(EcontGeoLocation location) {
    this.location = location;
  }

  public String getZip() {
    return zip;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }
}
