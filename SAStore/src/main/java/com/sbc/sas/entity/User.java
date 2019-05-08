package com.sbc.sas.entity;

import com.fasterxml.jackson.annotation.*;
import java.io.*;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "users")
public class User implements Serializable
{

  @Id
  @Column(name = "user_id")
  @SequenceGenerator(name = "SEQ_GEN_USER", sequenceName = "S_USERS", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GEN_USER")
  private Integer userId;

  @Column(name = "username")
  @Size(min = 4, max = 50)
  @NotNull
  private String username;

  @Column(name = "first_name")
  @Size(min = 4, max = 100)
  @NotNull
  private String firstName;

  @Column(name = "last_name")
//    @Size(min = 4, max = 100)
//    @NotNull
  private String lastName;

  @Column(name = "password")
  @Size(min = 4, max = 100)
  @JsonIgnore
  private String password;

  @Column(name = "email")
  @Size(max = 200)
  @Email
  @NotEmpty
  private String email;

  @Column(name = "phone")
  private String phone;

  @Column(name = "created_on")
  private Date createdOn;
  
  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "rolename"))
  private List<Role> roles = new ArrayList<>();

  public String getUserFullName()
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

  public String getUsername()
  {
    return username;
  }

  public void setUsername(String username)
  {
    this.username = username;
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

  public String getPassword()
  {
    return password;
  }

  public void setPassword(String password)
  {
    this.password = password;
  }

  public String getEmail()
  {
    return email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  public String getPhone()
  {
    return phone;
  }

  public void setPhone(String phone)
  {
    this.phone = phone;
  }

  public Date getCreatedOn()
  {
    return createdOn;
  }

  public void setCreatedOn(Date createdOn)
  {
    this.createdOn = createdOn;
  }

  public List<Role> getRoles()
  {
    return roles;
  }

  public void setRoles(List<Role> roles)
  {
    this.roles = roles;
  }

}
