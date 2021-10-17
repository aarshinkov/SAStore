package com.sastore.web.models.auth;

import java.io.Serializable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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
public class LoginModel implements Serializable {

  @NotBlank
  @Email
  @Size(max = 200)
  private String email;

  @NotBlank
  @Size(min = 2, max = 100)
  private String password;
}
