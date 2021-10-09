package com.sastore.web.integration.econt.nomenclatures.services;

import com.sastore.web.api.EcontApi;
import com.sastore.web.integration.econt.nomenclatures.EcontCity;
import com.sastore.web.integration.econt.nomenclatures.EcontCountry;
import com.sastore.web.integration.econt.nomenclatures.EcontOffice;
import com.sastore.web.integration.econt.nomenclatures.EcontQuarter;
import com.sastore.web.integration.econt.nomenclatures.EcontStreet;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Atanas Yordanov Arshinkov
 */
@Service
public class EcontNomenclaturesServiceImpl implements EcontNomenclaturesService {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private EcontApi econtApi;

  @Override
  public List<EcontCountry> getCountries() {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public List<EcontCity> getCities(String countryCode) {
    return econtApi.getCities(countryCode);
  }

  @Override
  public List<EcontOffice> getOffices(String countryCode, String cityID) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public List<EcontStreet> getStreets(String cityID) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public List<EcontQuarter> getQuarters(String cityID) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
}
