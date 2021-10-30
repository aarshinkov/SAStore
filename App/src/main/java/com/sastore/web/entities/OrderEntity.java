package com.sastore.web.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;

/**
 *
 * @author Atanas Yordanov Arshinkov
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "orders")
@DynamicInsert
public class OrderEntity implements Serializable {

  @Id
  @Column(name = "order_id")
  private String orderId;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id")
  private UserEntity user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "delivery_address")
  private OrderAddressEntity deliveryAddress;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "bill_address")
  private OrderAddressEntity billAddress;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "payment_type_id")
  private PaymentTypeEntity paymentType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "courier_id")
  private CourierEntity courier;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "status")
  private OrderStatusEntity status;

  @Column(name = "created_on")
  private Timestamp createdOn;

  @OneToMany(mappedBy = "order")
  private List<OrderProducts> orderProducts;
}
