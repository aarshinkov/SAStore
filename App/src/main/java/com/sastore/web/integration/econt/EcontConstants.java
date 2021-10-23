package com.sastore.web.integration.econt;

/**
 *
 * @author Atanas Yordanov Arshinkov
 */
public class EcontConstants {

  public static final String SEPARATOR = "/";
  public static final String SUFFIX = ".json";

  // Nomenclatures
  public static final String NOMENCLATURES = "Nomenclatures";
  public static final String NOMENCLATURES_SERVICE = "NomenclaturesService";
  public static final String ADDRESS_SERVICE = "AddressService";

  // Shipments
  public static String buildURL(String... urlPart) {

    if (urlPart == null) {
      return "";
    }

    if (urlPart[0].equals("")) {
      return "";
    }

    final StringBuilder builder = new StringBuilder();
    builder.append(SEPARATOR);

    for (int i = 0; i < urlPart.length; i++) {
      builder.append(urlPart[i]);

      if (i != urlPart.length - 1) {
        builder.append(SEPARATOR);
      }
    }

    return builder.toString();
  }
}
