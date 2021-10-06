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
 * @since 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "addresses")
@DynamicInsert
public class AddressEntity implements Serializable {

  @Id
  @Column(name = "address_id")
  private String addressId;

  @Column(name = "person_name")
  private String personName;

  @Column(name = "phone")
  private String phone;

  @Column(name = "country")
  private String country;

  @Column(name = "post_code")
  private String postCode;

  @Column(name = "province")
  private String province;

  @Column(name = "city")
  private String city;

  @Column(name = "district")
  private String district;

  @Column(name = "street")
  private String street;

  @Column(name = "street_no")
  private Integer streetNo;

  @Column(name = "enter")
  private String enter;

  @Column(name = "floor")
  private Integer floor;

  @Column(name = "apartment_no")
  private Integer apartmentNo;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private UserEntity user;

  @Column(name = "is_main")
  private Boolean isMain;
}
