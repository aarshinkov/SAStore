/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbc.sas.entity;

import java.io.*;
import javax.persistence.*;

@Entity
@Table(name = "countries")
public class Country implements Serializable
{
  @Id
  @Column(name = "country_name")
  private String countryName;

  @Column(name = "country_full")
  private String countryFull;

  public String getCountryName()
  {
    return countryName;
  }

  public void setCountryName(String countryName)
  {
    this.countryName = countryName;
  }

  public String getCountryFull()
  {
    return countryFull;
  }

  public void setCountryFull(String countryFull)
  {
    this.countryFull = countryFull;
  }

}
