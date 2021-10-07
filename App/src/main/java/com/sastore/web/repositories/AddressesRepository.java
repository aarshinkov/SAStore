package com.sastore.web.repositories;

import com.sastore.web.entities.AddressEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Repository
public interface AddressesRepository extends JpaRepository<AddressEntity, String> {

  List<AddressEntity> findAllByUserUserId(String userId);

  List<AddressEntity> findAllByUserUserIdOrderByAddedOnDesc(String userId);

  List<AddressEntity> findAllByUserUserIdAndIsMainTrueOrderByAddedOnDesc(String userId);

  AddressEntity findByAddressId(String addressId);
}
