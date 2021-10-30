package com.sastore.web.repositories;

import com.sastore.web.entities.OrderEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Atanas Yordanov Arshinkov
 */
@Repository
public interface OrdersRepository extends JpaRepository<OrderEntity, Integer> {

  @Override
  List<OrderEntity> findAll();
  
  OrderEntity findByOrderId(Integer orderId);
}
