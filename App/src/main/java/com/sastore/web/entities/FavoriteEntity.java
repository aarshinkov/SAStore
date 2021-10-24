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
@Table(name = "favorites")
@DynamicInsert
public class FavoriteEntity implements Serializable {

  @Id
  @Column(name = "favorite_id")
  private String favoriteId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private UserEntity user;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "product_id")
  private ProductEntity product;

  @Column(name = "added_on")
  private Timestamp addedOn;
}
