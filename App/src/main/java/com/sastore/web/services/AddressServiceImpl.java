package com.sastore.web.services;

import com.sastore.web.entities.AddressEntity;
import com.sastore.web.entities.UserEntity;
import com.sastore.web.models.AddressCreateModel;
import com.sastore.web.repositories.AddressesRepository;
import com.sastore.web.repositories.UsersRepository;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Service
public class AddressServiceImpl implements AddressService {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private AddressesRepository addressesRepository;

  @Autowired
  private UsersRepository usersRepository;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Override
  public List<AddressEntity> getUserAddresses(String userId) {

    return addressesRepository.findAllByUserUserId(userId);
  }

  @Override
  public AddressEntity createAddress(AddressCreateModel acm) {

    AddressEntity address = new AddressEntity();
    address.setAddressId(UUID.randomUUID().toString());
    address.setPersonName(acm.getPersonName());
    address.setPhone(acm.getPhone());
    address.setCountry(acm.getCountry());
    address.setPostCode(acm.getPostCode());
    address.setProvince(acm.getProvince());
    address.setCity(acm.getCity());
    if (acm.getDistrict().trim().length() == 0) {
      address.setDistrict(null);
    } else {
      address.setDistrict(acm.getDistrict());
    }
    address.setStreet(acm.getStreet());
    address.setStreetNo(acm.getStreetNo());
    if (acm.getEnter().trim().length() == 0) {
      address.setEnter(null);
    } else {
      address.setEnter(acm.getEnter());
    }
    address.setFloor(acm.getFloor());
    address.setApartmentNo(acm.getApartmentNo());

    UserEntity user = usersRepository.findByUserId(acm.getUserId());
    address.setUser(user);

    if (acm.getIsMain() == null) {
      address.setIsMain(false);
    } else {
      if (acm.getIsMain()) {
        final String updateMainAddresses = "UPDATE addresses SET is_main = false WHERE user_id = ?";
        jdbcTemplate.update(updateMainAddresses, acm.getUserId());
      }
      address.setIsMain(acm.getIsMain());
    }

    AddressEntity createdAddress = addressesRepository.save(address);

    return createdAddress;
  }

  @Override
  public boolean deleteAddress(String addressId) {

    AddressEntity address = addressesRepository.findByAddressId(addressId);

    if (address == null) {
      return false;
    }

    if (address.getIsMain()) {
      List<AddressEntity> addresses = addressesRepository.findAll();

      for (AddressEntity addr : addresses) {
        if (!addr.getAddressId().equals(addressId)) {
          final String updateMainAddress = "UPDATE addresses SET is_main = true WHERE address_id = ?";
          jdbcTemplate.update(updateMainAddress, addr.getAddressId());
          break;
        }
      }
    }

    addressesRepository.delete(address);

    return true;
  }
}
