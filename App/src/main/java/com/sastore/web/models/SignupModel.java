package com.sastore.web.models;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
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
public class SignupModel implements Serializable {

    @NotEmpty
    @Size(min = 2, max = 100)
    private String firstName;

    private String lastName;

    @NotEmpty
    @Email
    @Size(max = 200)
    private String email;

    @NotEmpty
    @Size(min = 2, max = 100)
    private String password;

    @NotEmpty
    @Size(min = 2, max = 100)
    private String confirmPassword;
}
