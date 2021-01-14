package com.sastore.web.entities;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

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
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private Double price;

    @Column(name = "avail_quant")
    private Integer availableQuantity;

    @Column(name = "description")
    private String description;

    @Column(name = "main_image")
    private String mainImage;

    @Column(name = "added_on")
    private Timestamp addedOn;

    @Column(name = "edited_on")
    private Timestamp editedOn;
}
