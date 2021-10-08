package com.sastore.web.filters;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductFilter implements Serializable {

  private String productId;
  private String title;
//    private Double price;

  public boolean isFilterEmpty() {

    return !(productId != null || title != null);
  }

  public String getPagingParams() {
    String result = "";

    if (productId != null) {
      result += "&productId=" + productId;
    }

    if (title != null) {
      result += "&title=" + title;
    }

//        if (price != null) {
//            result += "&price=" + price;
//        }
    return result;
  }
}
