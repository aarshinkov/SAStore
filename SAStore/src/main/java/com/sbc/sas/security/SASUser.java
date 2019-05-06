package com.sbc.sas.security;

import java.util.*;
import org.springframework.security.core.*;
import org.springframework.security.core.userdetails.*;

public class SASUser extends User
{
  private Integer userId;
  private String firstName;
  private String lastName;

  public SASUser(String username, String password, boolean enabled,
          boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
          Collection<? extends GrantedAuthority> authorities, String firstName, String lastName, Integer userId)
  {
    super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);

    this.firstName = firstName;
    this.lastName = lastName;
    this.userId = userId;
  }

  public String getFullName()
  {
    if (lastName == null || "".equalsIgnoreCase(lastName))
    {
      return firstName;
    }
    return firstName + " " + lastName;
  }

  public Integer getUserId()
  {
    return userId;
  }

  public void setUserId(Integer userId)
  {
    this.userId = userId;
  }

  public String getFirstName()
  {
    return firstName;
  }

  public void setFirstName(String firstName)
  {
    this.firstName = firstName;
  }

  public String getLastName()
  {
    return lastName;
  }

  public void setLastName(String lastName)
  {
    this.lastName = lastName;
  }

}
