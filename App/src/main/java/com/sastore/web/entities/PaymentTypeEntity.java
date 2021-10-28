package com.sastore.web.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "payment_types")
@DynamicInsert
public class PaymentTypeEntity implements Serializable {

  @Id
  @Column(name = "payment_type_id")
  private Integer paymentTypeId;

  @Column(name = "payment_type_descr")
  private String paymentTypeDescr;

  @Column(name = "is_active")
  private Boolean isActive;
}
