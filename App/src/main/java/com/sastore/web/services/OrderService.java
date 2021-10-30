package com.sastore.web.services;

import com.sastore.web.entities.OrderEntity;
import java.util.List;

/**
 *
 * @author Atanas Yordanov Arshinkov
 */
public interface OrderService {

  List<OrderEntity> getOrders();

  OrderEntity getOrder(String orderId);

  List<OrderEntity> getUserOrders(String userId);

  boolean processOrder(String orderId);

  boolean handToCourierOrder(String orderId);

  boolean finishOrder(String orderId);

  boolean cancelOrder(String orderId);

  Long getFinishedOrdersCount();
  
  Long getUserFinishedOrdersCount(String userId);
}
