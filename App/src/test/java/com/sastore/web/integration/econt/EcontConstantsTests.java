package com.sastore.web.integration.econt;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Atanas Yordanov Arshinkov
 */
public class EcontConstantsTests {

  @Test
  public void test_stringOne_returnAsExpected() {
    String buildURL = EcontConstants.buildURL("Atanas", "Hello", "You");
    Assert.assertEquals("/Atanas/Hello/You", buildURL);
  }

  @Test
  public void test_stringTwo_returnAsExpected() {
    String buildURL = EcontConstants.buildURL("Hello Username");
    Assert.assertEquals("/Hello Username", buildURL);
  }

  @Test
  public void test_stringEmpty_returnAsExpected() {
    String buildURL = EcontConstants.buildURL("");
    Assert.assertEquals("", buildURL);
  }

  @Test
  public void test_stringNull_returnEmpty() {
    String buildURL = EcontConstants.buildURL(null);
    Assert.assertEquals("", buildURL);
  }

  @Test
  public void test_fullLink_returnAsExpected() {
    String buildURL = EcontConstants.buildURL(EcontConstants.NOMENCLATURES, EcontConstants.NOMENCLATURES_SERVICE) + ".getCities" + EcontConstants.SUFFIX;
    Assert.assertEquals("/Nomenclatures/NomenclaturesService.getCities.json", buildURL);
  }
}
