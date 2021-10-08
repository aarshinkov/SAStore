package com.sastore.web.models;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
public class AddressEditModel implements Serializable {
  
  @NotBlank
  private String addressId;

  @NotBlank
  @Size(min = 1, max = 300)
  private String personName;

  @NotBlank
  @Size(min = 1, max = 100)
  private String phone;

  @NotBlank
  @Size(min = 1, max = 10)
  private String country;

  @NotBlank
  @Size(min = 1, max = 50)
  private String postCode;

  private String province;

  @NotBlank
  @Size(min = 1, max = 100)
  private String city;

  private String district;

  @NotBlank
  @Size(min = 1, max = 500)
  private String street;

  @NotNull
  private Integer streetNo;

  private String entrance;

  private Integer floor;

  private Integer apartmentNo;

  @NotBlank
  private String userId;

  private Boolean isMain;
}
