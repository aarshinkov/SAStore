package com.sastore.web.models;

import java.io.Serializable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
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
