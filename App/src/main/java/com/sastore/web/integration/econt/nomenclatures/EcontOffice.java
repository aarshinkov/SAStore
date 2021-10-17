package com.sastore.web.integration.econt.nomenclatures;

import com.sastore.web.integration.econt.EcontShipmentType;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author Atanas Yordanov Arshinkov
 */
public class EcontOffice implements Serializable {

  private Integer id;
  private String code;
  private Boolean isMPS; // Is mobile post station
  private Boolean isAPS; // Is automatic post station
  private String name;
  private String nameEn;
  private List<String> phones;
  private List<String> emails;
  private EcontAddress address;
  private String info;
  private String currency;
  private String language;
  private Timestamp normalBusinessHoursFrom;
  private Timestamp normalBusinessHoursTo;
  private Timestamp halfDayBusinessHoursFrom;
  private Timestamp halfDayBusinessHoursTo;
  private List<EcontShipmentType> shipmentTypes;
  private String partnerCode;
  private String hubCode;
  private String hubName;
  private String hubNameEn;

  public EcontOffice() {
  }

  public EcontOffice(Integer id, String code, Boolean isMPS, Boolean isAPS, String name, String nameEn, List<String> phones, List<String> emails, EcontAddress address, String info, String currency, String language, Timestamp normalBusinessHoursFrom, Timestamp normalBusinessHoursTo, Timestamp halfDayBusinessHoursFrom, Timestamp halfDayBusinessHoursTo, List<EcontShipmentType> shipmentTypes, String partnerCode, String hubCode, String hubName, String hubNameEn) {
    this.id = id;
    this.code = code;
    this.isMPS = isMPS;
    this.isAPS = isAPS;
    this.name = name;
    this.nameEn = nameEn;
    this.phones = phones;
    this.emails = emails;
    this.address = address;
    this.info = info;
    this.currency = currency;
    this.language = language;
    this.normalBusinessHoursFrom = normalBusinessHoursFrom;
    this.normalBusinessHoursTo = normalBusinessHoursTo;
    this.halfDayBusinessHoursFrom = halfDayBusinessHoursFrom;
    this.halfDayBusinessHoursTo = halfDayBusinessHoursTo;
    this.shipmentTypes = shipmentTypes;
    this.partnerCode = partnerCode;
    this.hubCode = hubCode;
    this.hubName = hubName;
    this.hubNameEn = hubNameEn;
  }

  @Override
  public String toString() {
    return "EcontOffice{" + "id=" + id + ", code=" + code + ", isMPS=" + isMPS + ", isAPS=" + isAPS + ", name=" + name + ", nameEn=" + nameEn + ", phones=" + phones + ", emails=" + emails + ", address=" + address + ", info=" + info + ", currency=" + currency + ", language=" + language + ", normalBusinessHoursFrom=" + normalBusinessHoursFrom + ", normalBusinessHoursTo=" + normalBusinessHoursTo + ", halfDayBusinessHoursFrom=" + halfDayBusinessHoursFrom + ", halfDayBusinessHoursTo=" + halfDayBusinessHoursTo + ", shipmentTypes=" + shipmentTypes + ", partnerCode=" + partnerCode + ", hubCode=" + hubCode + ", hubName=" + hubName + ", hubNameEn=" + hubNameEn + '}';
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Boolean getIsMPS() {
    return isMPS;
  }

  public void setIsMPS(Boolean isMPS) {
    this.isMPS = isMPS;
  }

  public Boolean getIsAPS() {
    return isAPS;
  }

  public void setIsAPS(Boolean isAPS) {
    this.isAPS = isAPS;
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

  public List<String> getPhones() {
    return phones;
  }

  public void setPhones(List<String> phones) {
    this.phones = phones;
  }

  public List<String> getEmails() {
    return emails;
  }

  public void setEmails(List<String> emails) {
    this.emails = emails;
  }

  public EcontAddress getAddress() {
    return address;
  }

  public void setAddress(EcontAddress address) {
    this.address = address;
  }

  public String getInfo() {
    return info;
  }

  public void setInfo(String info) {
    this.info = info;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public Timestamp getNormalBusinessHoursFrom() {
    return normalBusinessHoursFrom;
  }

  public void setNormalBusinessHoursFrom(Timestamp normalBusinessHoursFrom) {
    this.normalBusinessHoursFrom = normalBusinessHoursFrom;
  }

  public Timestamp getNormalBusinessHoursTo() {
    return normalBusinessHoursTo;
  }

  public void setNormalBusinessHoursTo(Timestamp normalBusinessHoursTo) {
    this.normalBusinessHoursTo = normalBusinessHoursTo;
  }

  public Timestamp getHalfDayBusinessHoursFrom() {
    return halfDayBusinessHoursFrom;
  }

  public void setHalfDayBusinessHoursFrom(Timestamp halfDayBusinessHoursFrom) {
    this.halfDayBusinessHoursFrom = halfDayBusinessHoursFrom;
  }

  public Timestamp getHalfDayBusinessHoursTo() {
    return halfDayBusinessHoursTo;
  }

  public void setHalfDayBusinessHoursTo(Timestamp halfDayBusinessHoursTo) {
    this.halfDayBusinessHoursTo = halfDayBusinessHoursTo;
  }

  public List<EcontShipmentType> getShipmentTypes() {
    return shipmentTypes;
  }

  public void setShipmentTypes(List<EcontShipmentType> shipmentTypes) {
    this.shipmentTypes = shipmentTypes;
  }

  public String getPartnerCode() {
    return partnerCode;
  }

  public void setPartnerCode(String partnerCode) {
    this.partnerCode = partnerCode;
  }

  public String getHubCode() {
    return hubCode;
  }

  public void setHubCode(String hubCode) {
    this.hubCode = hubCode;
  }

  public String getHubName() {
    return hubName;
  }

  public void setHubName(String hubName) {
    this.hubName = hubName;
  }

  public String getHubNameEn() {
    return hubNameEn;
  }

  public void setHubNameEn(String hubNameEn) {
    this.hubNameEn = hubNameEn;
  }
}
