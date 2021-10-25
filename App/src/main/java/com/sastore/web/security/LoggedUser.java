package com.sastore.web.security;

import java.sql.Timestamp;
import java.util.Collection;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 *
 * @author Atanas Yordanov Arshinkov
 */
@Getter
@Setter
@ToString
public class LoggedUser extends User {

  private String userId;
  private String email;
  private String firstName;
  private String lastName;
  private String avatar;
  private Boolean isActive;
  private Timestamp createdOn;
  private Timestamp editedOn;
  private Timestamp loggedOn;
  private String userAgent;

  public LoggedUser(String username, String password, boolean enabled,
          boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
          Collection<? extends GrantedAuthority> authorities,
          String userId, String firstName, String lastName, String avatar, Boolean isActive, Timestamp createdOn, Timestamp editedOn) {

    super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    this.userId = userId;
    this.email = username;
    this.firstName = firstName;
    this.lastName = lastName;
    this.avatar = avatar;
    this.isActive = isActive;
    this.createdOn = createdOn;
    this.editedOn = editedOn;
  }

  public String getFullName() {
    return (lastName != null) ? firstName + ' ' + lastName : firstName;
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
//    return this.isActive;
    return true;
  }
}
