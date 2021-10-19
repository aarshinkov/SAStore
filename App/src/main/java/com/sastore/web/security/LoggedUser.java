package com.sastore.web.security;

import java.sql.Timestamp;
import java.util.Collection;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 *
 * @author Atanas Yordanov Arshinkov
 */
@Getter
@Setter
public class LoggedUser extends User {

  private String userId;
  private String firstName;
  private String lastName;
  private String avatar;
  private Boolean isActive;
  private Timestamp createdOn;
  private Timestamp editedOn;

  public LoggedUser(String username, String password, boolean enabled,
          boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
          Collection<? extends GrantedAuthority> authorities,
          String userId, String firstName, String lastName, String avatar, Boolean isActive, Timestamp createdOn, Timestamp editedOn) {

    super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    this.userId = userId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.avatar = avatar;
    this.isActive = isActive;
    this.createdOn = createdOn;
    this.editedOn = editedOn;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return this.isActive;
  }
}
