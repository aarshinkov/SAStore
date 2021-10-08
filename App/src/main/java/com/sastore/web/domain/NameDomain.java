package com.sastore.web.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NameDomain implements Serializable {

  private String firstName;
  private String lastName;

  public String getFullName() {
    return (lastName != null) ? firstName + ' ' + lastName : firstName;
  }

  @Override
  public String toString() {
    return getFullName();
  }
}
