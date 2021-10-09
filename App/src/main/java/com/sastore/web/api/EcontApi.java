package com.sastore.web.api;

import com.sastore.web.utils.LogRequest;
import static com.sastore.web.utils.Methods.DELETE;
import static com.sastore.web.utils.Methods.GET;
import static com.sastore.web.utils.Methods.POST;
import static com.sastore.web.utils.Methods.PUT;
import com.google.gson.Gson;
import com.sastore.web.integration.econt.EcontConstants;
import com.sastore.web.integration.econt.nomenclatures.EcontCity;
import com.sastore.web.integration.econt.nomenclatures.requests.EcontCountryCodeRequest;
import com.sastore.web.integration.econt.nomenclatures.responses.EcontCitiesResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Class responsible for making request to the API and by so providing the link
 * with the database
 *
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
public class EcontApi extends Api {

  private final Logger log = LoggerFactory.getLogger(getClass());

  private final Gson PARSER = new Gson();

  @Autowired
  private RestTemplate restTemplate;

  public List<EcontCity> getCities(String countryCode) {
    HttpHeaders headers = getHeaders();
//    headers.set("Authorization", getAuthorizationToken(request));

    final EcontCountryCodeRequest eccr = new EcontCountryCodeRequest();
    eccr.setCountryCode(countryCode);

    HttpEntity entity = new HttpEntity(eccr, headers);

    final String url = getEcontApiUrl() + EcontConstants.buildURL(EcontConstants.NOMENCLATURES, EcontConstants.NOMENCLATURES_SERVICE) + ".getCities" + EcontConstants.SUFFIX;

    LogRequest logRequest = new LogRequest(POST.getMethod(), url);
    log.debug(logRequest.toString());

    try {
      ResponseEntity<EcontCitiesResponse> response = restTemplate.postForEntity(url, entity, EcontCitiesResponse.class);

      if (response.getBody() == null) {
        return new ArrayList<>();
      }

      return response.getBody().getCities() != null ? response.getBody().getCities() : new ArrayList<>();
    } catch (HttpClientErrorException e) {
      String responseBody = e.getResponseBodyAsString();
      Gson g = new Gson();
      return new ArrayList<>();
//      throw g.fromJson(responseBody, FieldValidationException.class);
    }
  }
}
