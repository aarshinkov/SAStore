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
public class UserFilter implements Serializable {

    private String email;
    private String firstName;
    private String lastName;
//    private Boolean isActive;
}
