package com.sastore.web.integration.econt.nomenclatures;

import java.io.Serializable;

/**
 *
 * @author Atanas Yordanov Arshinkov
 */
public class EcontQuarter implements Serializable {

  private Integer id;
  private Integer cityID;
  private String name;
  private String nameEn;

  public EcontQuarter() {
  }

  public EcontQuarter(Integer id, Integer cityID, String name, String nameEn) {
    this.id = id;
    this.cityID = cityID;
    this.name = name;
    this.nameEn = nameEn;
  }

  @Override
  public String toString() {
    return "EcontQuarter{" + "id=" + id + ", cityID=" + cityID + ", name=" + name + ", nameEn=" + nameEn + '}';
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getCityID() {
    return cityID;
  }

  public void setCityID(Integer cityID) {
    this.cityID = cityID;
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
}
