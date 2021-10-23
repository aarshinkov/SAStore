package com.sastore.web.integration.econt.nomenclatures;

import java.io.Serializable;

/**
 *
 * @author Atanas Yordanov Arshinkov
 */
public class EcontGeoLocation implements Serializable {

  private Double latitude;
  private Double longitude;
  private Integer confidence;

  public EcontGeoLocation() {
  }

  public EcontGeoLocation(Double latitude, Double longitude, Integer confidence) {
    this.latitude = latitude;
    this.longitude = longitude;
    this.confidence = confidence;
  }

  @Override
  public String toString() {
    return "EcontGeoLocation{" + "latitude=" + latitude + ", longitude=" + longitude + ", confidence=" + confidence + '}';
  }

  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  public Integer getConfidence() {
    return confidence;
  }

  public void setConfidence(Integer confidence) {
    this.confidence = confidence;
  }
}
