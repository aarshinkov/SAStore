package com.sastore.web.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "order_products")
@DynamicInsert
public class OrderProducts implements Serializable {

  @Id
  @Column(name = "order_product_id")
  private String orderProductId;

  @ManyToOne
  @JoinColumn(name = "order_id")
  private OrderEntity order;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id")
  private ProductEntity product;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_product_variation_id")
  private OrderProductVariationEntity orderProductVariation;

  @Column(name = "quantity")
  private Integer quantity;

  @Column(name = "price_per_unit")
  private Double pricePerUnit;
}
