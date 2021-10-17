package com.sastore.web.integration.econt.nomenclatures;

import java.io.Serializable;

/**
 *
 * @author Atanas Yordanov Arshinkov
 */
public class EcontCity implements Serializable {

  private Integer id;
  private EcontCountry country;
  private String postCode;
  private String name;
  private String nameEn;
  private String regionName;
  private String regionNameEn;
  private String phoneCode;
  private EcontGeoLocation location;
  private Boolean expressCityDeliveries;

  public EcontCity() {
  }

  public EcontCity(Integer id, EcontCountry country, String postCode, String name, String nameEn, String regionName, String regionNameEn, String phoneCode, EcontGeoLocation location, Boolean expressCityDeliveries) {
    this.id = id;
    this.country = country;
    this.postCode = postCode;
    this.name = name;
    this.nameEn = nameEn;
    this.regionName = regionName;
    this.regionNameEn = regionNameEn;
    this.phoneCode = phoneCode;
    this.location = location;
    this.expressCityDeliveries = expressCityDeliveries;
  }

  @Override
  public String toString() {
    return "EcontCity{" + "id=" + id + ", country=" + country + ", postCode=" + postCode + ", name=" + name + ", nameEn=" + nameEn + ", regionName=" + regionName + ", regionNameEn=" + regionNameEn + ", phoneCode=" + phoneCode + ", location=" + location + ", expressCityDeliveries=" + expressCityDeliveries + '}';
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public EcontCountry getCountry() {
    return country;
  }

  public void setCountry(EcontCountry country) {
    this.country = country;
  }

  public String getPostCode() {
    return postCode;
  }

  public void setPostCode(String postCode) {
    this.postCode = postCode;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getNameEn() {
    return nameEn;
  }

  public void setNameEn(String nameEn) {
    this.nameEn = nameEn;
  }

  public String getRegionName() {
    return regionName;
  }

  public void setRegionName(String regionName) {
    this.regionName = regionName;
  }

  public String getRegionNameEn() {
    return regionNameEn;
  }

  public void setRegionNameEn(String regionNameEn) {
    this.regionNameEn = regionNameEn;
  }

  public String getPhoneCode() {
    return phoneCode;
  }

  public void setPhoneCode(String phoneCode) {
    this.phoneCode = phoneCode;
  }

  public EcontGeoLocation getLocation() {
    return location;
  }

  public void setLocation(EcontGeoLocation location) {
    this.location = location;
  }

  public Boolean getExpressCityDeliveries() {
    return expressCityDeliveries;
  }

  public void setExpressCityDeliveries(Boolean expressCityDeliveries) {
    this.expressCityDeliveries = expressCityDeliveries;
  }
}
