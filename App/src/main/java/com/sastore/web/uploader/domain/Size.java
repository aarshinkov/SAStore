package com.sastore.web.uploader.domain;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
public class Size {

  private static final Integer CONVERSION_NUM = 1024;

  public static Double getByteSize(Long length) {
    return (double) length;
  }

  public static Double getKiloBytesSize(Long length) {
    return (double) length / CONVERSION_NUM;
  }

  public static Double getMegaBytesSize(Long length) {
    return (double) length / (Math.pow(CONVERSION_NUM, 2));
  }

  public static Double getGigaBytesSize(Long length) {
    return (double) length / (Math.pow(CONVERSION_NUM, 3));
  }

  public static Double getTeraBytesSize(Long length) {
    return (double) length / (Math.pow(CONVERSION_NUM, 4));
  }
}
