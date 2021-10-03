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
@Table(name = "basket_products")
@DynamicInsert
public class BasketProductEntity implements Serializable {

  @Id
  @Column(name = "basket_product_id")
  private String basketProductId;

  @ManyToOne
  @JoinColumn(name = "basket_id")
  private BasketEntity basket;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private ProductEntity product;

  @Column(name = "added_on")
  private Timestamp addedOn;
}
