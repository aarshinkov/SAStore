package com.sastore.web.integration.econt;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Atanas Yordanov Arshinkov
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EcontCity implements Serializable {

  private Integer id;
  private String code2;
  private String code3;
  private String name;
  private String nameEn;
  private Boolean isEU;
}
