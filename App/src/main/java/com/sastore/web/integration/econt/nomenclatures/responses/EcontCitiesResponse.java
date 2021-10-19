package com.sastore.web.integration.econt.nomenclatures.responses;

import com.sastore.web.integration.econt.nomenclatures.EcontCity;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Atanas Yordanov Arshinkov
 */
public class EcontCitiesResponse implements Serializable {

  private List<EcontCity> cities;

  public EcontCitiesResponse() {
  }

  public EcontCitiesResponse(List<EcontCity> cities) {
    this.cities = cities;
  }

  @Override
  public String toString() {
    return "EcontCitiesResponse{" + "cities=" + cities + '}';
  }

  public List<EcontCity> getCities() {
    return cities;
  }

  public void setCities(List<EcontCity> cities) {
    this.cities = cities;
  }
}
