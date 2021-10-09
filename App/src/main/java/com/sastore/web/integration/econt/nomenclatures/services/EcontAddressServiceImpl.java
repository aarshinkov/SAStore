package com.sastore.web.integration.econt.nomenclatures.services;

import com.sastore.web.integration.econt.nomenclatures.EcontAddress;
import com.sastore.web.integration.econt.nomenclatures.responses.EcontAddressValidateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author Atanas Yordanov Arshinkov
 */
@Service
public class EcontAddressServiceImpl implements EcontAddressService {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Override
  public EcontAddressValidateResponse validateAddress(EcontAddress address) {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
}
