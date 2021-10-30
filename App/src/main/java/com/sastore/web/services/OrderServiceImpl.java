package com.sastore.web.services;

import com.sastore.web.entities.OrderEntity;
import com.sastore.web.repositories.OrdersRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author Atanas Yordanov Arshinkov
 */
@Service
public class OrderServiceImpl implements OrderService {

  private final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private OrdersRepository ordersRepository;

  @Override
  public List<OrderEntity> getOrders() {
    return ordersRepository.findAll();
  }

  @Override
  public OrderEntity getOrder(String orderId) {
    return ordersRepository.findByOrderId(orderId);
  }

  @Override
  public List<OrderEntity> getUserOrders(String userId) {
    return ordersRepository.findByUserUserId(userId);
  }

  @Override
  public boolean processOrder(String orderId) {
    log.debug("Changing order (" + orderId + ") status to Processing");
    return changeOrderStatus(orderId, 2);
  }

  @Override
  public boolean handToCourierOrder(String orderId) {
    log.debug("Changing order (" + orderId + ") status to In courier");
    return changeOrderStatus(orderId, 3);
  }

  @Override
  public boolean finishOrder(String orderId) {
    log.debug("Changing order (" + orderId + ") status to Finished");
    return changeOrderStatus(orderId, 4);
  }

  @Override
  public boolean cancelOrder(String orderId) {
    log.debug("Changing order (" + orderId + ") status to Canceled");
    return changeOrderStatus(orderId, 5);
  }

  @Override
  public Long getFinishedOrdersCount() {
    // 4 - Order finished
    return ordersRepository.countByStatusOrderStatusId(4);
  }

  @Override
  public Long getUserFinishedOrdersCount(String userId) {
    return ordersRepository.countByStatusOrderStatusIdAndUserUserId(4, userId);
  }

  private boolean changeOrderStatus(String orderId, Integer status) {

    final String sql = "UPDATE orders SET status = ? WHERE order_id = ?";

    int rows = jdbcTemplate.update(sql, status, orderId);

    return rows == 1;
  }
}
