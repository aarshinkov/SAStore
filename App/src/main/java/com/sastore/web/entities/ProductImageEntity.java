package com.sastore.web.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
@Table(name = "prod_images")
@DynamicInsert
public class ProductImageEntity implements Serializable {

    @Id
    @Column(name = "image_id")
    private String imageId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @Column(name = "is_main")
    private Boolean isMain;

    @Column(name = "created_on")
    private Timestamp createdOn;
}
