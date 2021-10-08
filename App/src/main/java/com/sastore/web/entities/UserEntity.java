package com.sastore.web.entities;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Atanas Yordanov Arshinkov
 * @since 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@DynamicInsert
public class UserEntity implements UserDetails, Serializable {

  @Id
  @Column(name = "user_id")
  private String userId;

  @Column(name = "email")
  private String email;

  @Column(name = "password")
  private String password;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "avatar")
  private String avatar;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "created_on")
  private Timestamp createdOn;

  @Column(name = "edited_on")
  private Timestamp editedOn;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "rolename"))
  private List<RoleEntity> roles;

  public String getFullName() {
    return (lastName != null) ? firstName + ' ' + lastName : firstName;
  }

  @Override
  public String toString() {
    return getFullName();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<GrantedAuthority> authorities = new ArrayList<>();

    for (RoleEntity role : roles) {
      authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRolename()));
    }

    return authorities;
  }

  @Override
  public String getUsername() {
    return this.email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
//        return isActive;
    return true;
  }
}
