package com.sastore.web.filters;

import com.sastore.web.enums.Order;
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
public class UserFilter implements Serializable {

    private String userId;
    private String email;
    private String firstName;
    private String lastName;
    private Boolean isActive;
    private String role;
    private String order = Order.DESCENDING.getOrder();

    public String getPagingParams() {
        String result = "";

        if (userId != null) {
            result += "&userId=" + userId;
        }

        if (email != null) {
            result += "&email=" + email;
        }

        if (firstName != null) {
            result += "&firstName=" + firstName;
        }

        if (lastName != null) {
            result += "&lastName=" + lastName;
        }

        if (isActive != null) {
            result += "&active=" + isActive;
        }

        if (role != null) {
            result += "&role=" + role;
        }

        if (order != null) {
            result += "&order=" + order;
        }

        return result;
    }
}
