package com.sastore.web.services;

import com.sastore.web.entities.AddressEntity;
import com.sastore.web.models.AddressCreateModel;
import java.util.List;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
public interface AddressService {

  List<AddressEntity> getUserAddresses(String userId);
  
  AddressEntity createAddress(AddressCreateModel acm);
  
  boolean deleteAddress(String addressId);
}
