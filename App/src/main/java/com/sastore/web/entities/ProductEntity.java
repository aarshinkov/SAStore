package com.sastore.web.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "products")
@DynamicInsert
public class ProductEntity implements Serializable {

  @Id
//  @SequenceGenerator(name = "seq_gen_product", sequenceName = "s_products", allocationSize = 1)
//  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen_product")
  @Column(name = "product_id")
  private String productId;

  @Column(name = "title")
  private String title;

  @Column(name = "price")
  private Double price;

  @Column(name = "discount")
  private Double discount;

  @Column(name = "avail_quant")
  private Integer availableQuantity;

  @Column(name = "views")
  private Integer views;

  @Column(name = "description")
  private String description;

  @Column(name = "status")
  private Integer status;

  @Column(name = "main_image")
  private String mainImage;

  @Column(name = "added_on")
  private Timestamp addedOn;

  @Column(name = "approved_on")
  private Timestamp approvedOn;

  @Column(name = "edited_on")
  private Timestamp editedOn;

  @Transient
  private String formattedDescription;

  @Transient
  private Boolean isNew;

  @Transient
  private Boolean isFavorite;
}
