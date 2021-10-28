package com.sastore.web.entities;

import java.io.Serializable;
import java.sql.Timestamp;
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
@Table(name = "order_product_variations")
@DynamicInsert
public class OrderProductVariationEntity implements Serializable {

  @Id
  @Column(name = "order_product_variation_id")
  private String orderProductVariationId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id")
  private ProductEntity product;

  @Column(name = "variation")
  private String variation;

  @Column(name = "is_color")
  private Boolean isColor;

  @Column(name = "is_dimension")
  private Boolean isDimension;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "assosiated_image")
  private ProductImageEntity assosiatedImage;
}
