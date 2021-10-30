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
public interface OrdersRepository extends JpaRepository<OrderEntity, String> {

  @Override
  List<OrderEntity> findAll();

  List<OrderEntity> findByUserUserId(String userId);

  OrderEntity findByOrderId(String orderId);

  Long countByStatusOrderStatusId(Integer orderStatusId);

  Long countByStatusOrderStatusIdAndUserUserId(Integer orderStatusId, String userId);
}
