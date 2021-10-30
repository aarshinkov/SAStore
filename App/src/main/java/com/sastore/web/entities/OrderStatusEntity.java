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
@Table(name = "order_statuses")
@DynamicInsert
public class OrderStatusEntity implements Serializable {

  @Id
  @Column(name = "order_status_id")
  private Integer orderStatusId;

  @Column(name = "order_status_descr")
  private String orderStatusDescr;
}
