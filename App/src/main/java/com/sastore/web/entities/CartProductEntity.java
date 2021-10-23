package com.sastore.web.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
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
 * @since 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "cart_products")
@DynamicInsert
public class CartProductEntity implements Serializable {

  @Id
  @Column(name = "cart_product_id")
  private String cartProductId;

  @ManyToOne
  @JoinColumn(name = "cart_id")
  private CartEntity cart;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private ProductEntity product;

  @Column(name = "added_on")
  private Timestamp addedOn;
}
