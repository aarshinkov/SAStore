package com.sastore.web.filters;

import lombok.*;

import java.io.Serializable;

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

    private Long productId;
    private String title;
//    private Double price;

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
