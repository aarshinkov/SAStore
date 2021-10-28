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
@Table(name = "couriers")
@DynamicInsert
public class CourierEntity implements Serializable {

  @Id
  @Column(name = "courier_id")
  private Integer courierId;

  @Column(name = "courier_name")
  private String courierName;
  
  @Column(name = "is_active")
  private Boolean isActive;
}
