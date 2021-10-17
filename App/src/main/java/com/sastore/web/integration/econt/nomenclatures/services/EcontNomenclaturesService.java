package com.sastore.web.integration.econt.nomenclatures.services;

import com.sastore.web.integration.econt.nomenclatures.EcontCity;
import com.sastore.web.integration.econt.nomenclatures.EcontCountry;
import com.sastore.web.integration.econt.nomenclatures.EcontOffice;
import com.sastore.web.integration.econt.nomenclatures.EcontQuarter;
import com.sastore.web.integration.econt.nomenclatures.EcontStreet;
import java.util.List;

/**
 *
 * @author Atanas Yordanov Arshinkov
 */
public interface EcontNomenclaturesService {

  List<EcontCountry> getCountries();

  List<EcontCity> getCities(String countryCode);

  List<EcontOffice> getOffices(String countryCode, String cityID);

  List<EcontStreet> getStreets(String cityID);

  List<EcontQuarter> getQuarters(String cityID);
}
