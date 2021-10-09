package com.sastore.web.integration.econt.nomenclatures.services;

import com.sastore.web.integration.econt.nomenclatures.EcontAddress;
import com.sastore.web.integration.econt.nomenclatures.responses.EcontAddressValidateResponse;

/**
 *
 * @author Atanas Yordanov Arshinkov
 */
public interface EcontAddressService {

  EcontAddressValidateResponse validateAddress(EcontAddress address);
}
