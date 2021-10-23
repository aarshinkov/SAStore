package com.sastore.web.services;

import com.sastore.web.entities.AddressEntity;
import com.sastore.web.models.addresses.AddressCreateModel;
import com.sastore.web.models.addresses.AddressEditModel;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
public interface AddressService {

  List<AddressEntity> getUserAddresses(String userId);

  AddressEntity getUserMainAddress(String userId);

  AddressEntity getUserAddress(String addressId);

  AddressEntity createAddress(AddressCreateModel acm);

  AddressEntity editAddress(AddressEditModel aem, HttpServletRequest request) throws Exception;

  boolean deleteAddress(String addressId);
}
