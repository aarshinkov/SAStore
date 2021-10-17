package com.sastore.web.services;

import com.sastore.web.entities.AddressEntity;
import com.sastore.web.entities.UserEntity;
import com.sastore.web.models.addresses.AddressCreateModel;
import com.sastore.web.models.addresses.AddressEditModel;
import com.sastore.web.repositories.AddressesRepository;
import com.sastore.web.repositories.UsersRepository;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    return addressesRepository.findAllByUserUserIdOrderByAddedOnDesc(userId);
  }

  @Override
  public AddressEntity getUserMainAddress(String userId) {

    List<AddressEntity> mainAddresses = addressesRepository.findAllByUserUserIdAndIsMainTrueOrderByAddedOnDesc(userId);

    if (mainAddresses != null) {
      if (!mainAddresses.isEmpty()) {
        return mainAddresses.get(0);
      }
    }

    return null;
  }

  @Override
  public AddressEntity getUserAddress(String addressId) {
    return addressesRepository.findByAddressId(addressId);
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
    if (acm.getEntrance().trim().length() == 0) {
      address.setEntrance(null);
    } else {
      address.setEntrance(acm.getEntrance());
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
  public AddressEntity editAddress(AddressEditModel aem, HttpServletRequest request) throws Exception {

    AddressEntity address = addressesRepository.findByAddressId(aem.getAddressId());

    if (address == null) {
      throw new Exception("Address does not exist");
    }

    address.setPersonName(aem.getPersonName());
    address.setPhone(aem.getPhone());
    address.setCountry(aem.getCountry());
    address.setPostCode(aem.getPostCode());
    address.setProvince(aem.getProvince());
    address.setCity(aem.getCity());
    if (aem.getDistrict().trim().length() == 0) {
      address.setDistrict(null);
    } else {
      address.setDistrict(aem.getDistrict());
    }
    address.setStreet(aem.getStreet());
    address.setStreetNo(aem.getStreetNo());
    if (aem.getEntrance().trim().length() == 0) {
      address.setEntrance(null);
    } else {
      address.setEntrance(aem.getEntrance());
    }
    address.setFloor(aem.getFloor());
    address.setApartmentNo(aem.getApartmentNo());

    HttpSession session = request.getSession();

    if (address.getIsMain()) {
      // main - main
      if (aem.getIsMain()) {
        session.setAttribute("mainAddressCountry", aem.getCountry());
        session.setAttribute("mainAddressCity", aem.getCity());
      } // main - sec
      else {
        // Find first address and assign main to it
        List<AddressEntity> addresses = addressesRepository.findAllByUserUserIdOrderByAddedOnDesc(aem.getUserId());

        if (addresses != null) {
          if (addresses.size() >= 2) {
            for (AddressEntity tempAddress : addresses) {
              if (!tempAddress.getAddressId().equals(aem.getAddressId())) {
                final String updateMainAddress = "UPDATE addresses SET is_main = true WHERE address_id = ?";
                jdbcTemplate.update(updateMainAddress, tempAddress.getAddressId());

                session.setAttribute("mainAddressCountry", aem.getCountry());
                session.setAttribute("mainAddressCity", aem.getCity());
                break;
              }
            }
          }
        }
      }
    } else {
      // sec - main
      if (aem.getIsMain()) {
        // Remove current main and assign new main
        final String updateMainAddresses = "UPDATE addresses SET is_main = false WHERE user_id = ?";
        jdbcTemplate.update(updateMainAddresses, aem.getUserId());

        session.setAttribute("mainAddressCountry", aem.getCountry());
        session.setAttribute("mainAddressCity", aem.getCity());
      } // sec - sec
      else {

      }
    }

    if (aem.getIsMain() == null) {
      address.setIsMain(false);
    } else {
      address.setIsMain(aem.getIsMain());
    }

    address.setEditedOn(new Timestamp(System.currentTimeMillis()));

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
