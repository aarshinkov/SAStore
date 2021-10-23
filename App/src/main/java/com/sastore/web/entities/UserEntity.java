package com.sastore.web.entities;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
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
public class UserEntity implements Serializable {

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
}
