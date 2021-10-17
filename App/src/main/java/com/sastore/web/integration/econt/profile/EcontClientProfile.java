package com.sastore.web.integration.econt.profile;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Atanas Yordanov Arshinkov
 */
public class EcontClientProfile implements Serializable {

  private Integer id;
  private String name;
  private String nameEn;
  private List<String> phones;
  private String email;
  private List<String> skypeAccounts;
  private String clientNumber;
  private String clientNumberEn;
  private Boolean juridicalEntity;
  private String personalIDType;
  private String personalIDNumber;
  private String companyType;
  private String ein;
  private String ddsEinPrefix;
  private String ddsEin;
  private String registrationAddress;
  private String molName;
  private String molEGN;
  private String molIDNum;

  public EcontClientProfile() {
  }

  public EcontClientProfile(Integer id, String name, String nameEn, List<String> phones, String email, List<String> skypeAccounts, String clientNumber, String clientNumberEn, Boolean juridicalEntity, String personalIDType, String personalIDNumber, String companyType, String ein, String ddsEinPrefix, String ddsEin, String registrationAddress, String molName, String molEGN, String molIDNum) {
    this.id = id;
    this.name = name;
    this.nameEn = nameEn;
    this.phones = phones;
    this.email = email;
    this.skypeAccounts = skypeAccounts;
    this.clientNumber = clientNumber;
    this.clientNumberEn = clientNumberEn;
    this.juridicalEntity = juridicalEntity;
    this.personalIDType = personalIDType;
    this.personalIDNumber = personalIDNumber;
    this.companyType = companyType;
    this.ein = ein;
    this.ddsEinPrefix = ddsEinPrefix;
    this.ddsEin = ddsEin;
    this.registrationAddress = registrationAddress;
    this.molName = molName;
    this.molEGN = molEGN;
    this.molIDNum = molIDNum;
  }

  @Override
  public String toString() {
    return "EcontClientProfile{" + "id=" + id + ", name=" + name + ", nameEn=" + nameEn + ", phones=" + phones + ", email=" + email + ", skypeAccounts=" + skypeAccounts + ", clientNumber=" + clientNumber + ", clientNumberEn=" + clientNumberEn + ", juridicalEntity=" + juridicalEntity + ", personalIDType=" + personalIDType + ", personalIDNumber=" + personalIDNumber + ", companyType=" + companyType + ", ein=" + ein + ", ddsEinPrefix=" + ddsEinPrefix + ", ddsEin=" + ddsEin + ", registrationAddress=" + registrationAddress + ", molName=" + molName + ", molEGN=" + molEGN + ", molIDNum=" + molIDNum + '}';
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
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

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public List<String> getSkypeAccounts() {
    return skypeAccounts;
  }

  public void setSkypeAccounts(List<String> skypeAccounts) {
    this.skypeAccounts = skypeAccounts;
  }

  public String getClientNumber() {
    return clientNumber;
  }

  public void setClientNumber(String clientNumber) {
    this.clientNumber = clientNumber;
  }

  public String getClientNumberEn() {
    return clientNumberEn;
  }

  public void setClientNumberEn(String clientNumberEn) {
    this.clientNumberEn = clientNumberEn;
  }

  public Boolean getJuridicalEntity() {
    return juridicalEntity;
  }

  public void setJuridicalEntity(Boolean juridicalEntity) {
    this.juridicalEntity = juridicalEntity;
  }

  public String getPersonalIDType() {
    return personalIDType;
  }

  public void setPersonalIDType(String personalIDType) {
    this.personalIDType = personalIDType;
  }

  public String getPersonalIDNumber() {
    return personalIDNumber;
  }

  public void setPersonalIDNumber(String personalIDNumber) {
    this.personalIDNumber = personalIDNumber;
  }

  public String getCompanyType() {
    return companyType;
  }

  public void setCompanyType(String companyType) {
    this.companyType = companyType;
  }

  public String getEin() {
    return ein;
  }

  public void setEin(String ein) {
    this.ein = ein;
  }

  public String getDdsEinPrefix() {
    return ddsEinPrefix;
  }

  public void setDdsEinPrefix(String ddsEinPrefix) {
    this.ddsEinPrefix = ddsEinPrefix;
  }

  public String getDdsEin() {
    return ddsEin;
  }

  public void setDdsEin(String ddsEin) {
    this.ddsEin = ddsEin;
  }

  public String getRegistrationAddress() {
    return registrationAddress;
  }

  public void setRegistrationAddress(String registrationAddress) {
    this.registrationAddress = registrationAddress;
  }

  public String getMolName() {
    return molName;
  }

  public void setMolName(String molName) {
    this.molName = molName;
  }

  public String getMolEGN() {
    return molEGN;
  }

  public void setMolEGN(String molEGN) {
    this.molEGN = molEGN;
  }

  public String getMolIDNum() {
    return molIDNum;
  }

  public void setMolIDNum(String molIDNum) {
    this.molIDNum = molIDNum;
  }
}
