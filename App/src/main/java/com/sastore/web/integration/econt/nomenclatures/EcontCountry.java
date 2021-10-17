package com.sastore.web.integration.econt.nomenclatures;

import java.io.Serializable;

/**
 *
 * @author Atanas Yordanov Arshinkov
 */
public class EcontCountry implements Serializable {

  private Integer id;
  private String code2;
  private String code3;
  private String name;
  private String nameEn;
  private Boolean isEU;

  public EcontCountry() {
  }

  public EcontCountry(Integer id, String code2, String code3, String name, String nameEn, Boolean isEU) {
    this.id = id;
    this.code2 = code2;
    this.code3 = code3;
    this.name = name;
    this.nameEn = nameEn;
    this.isEU = isEU;
  }

  @Override
  public String toString() {
    return "EcontCountry{" + "id=" + id + ", code2=" + code2 + ", code3=" + code3 + ", name=" + name + ", nameEn=" + nameEn + ", isEU=" + isEU + '}';
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getCode2() {
    return code2;
  }

  public void setCode2(String code2) {
    this.code2 = code2;
  }

  public String getCode3() {
    return code3;
  }

  public void setCode3(String code3) {
    this.code3 = code3;
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

  public Boolean getIsEU() {
    return isEU;
  }

  public void setIsEU(Boolean isEU) {
    this.isEU = isEU;
  }
}
