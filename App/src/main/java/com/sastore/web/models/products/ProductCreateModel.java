package com.sastore.web.models.products;

import java.io.Serializable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
public class ProductCreateModel implements Serializable {

  @NotBlank
  private String title;

  @NotNull
  private Double price;

  @NotNull
  @Min(0)
  private Integer availableQuantity;

  @NotBlank
  private String description;
}
