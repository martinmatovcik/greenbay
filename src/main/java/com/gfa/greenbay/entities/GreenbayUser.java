package com.gfa.greenbay.entities;

import com.gfa.greenbay.enums.Role;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
public class GreenbayUser implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;
  private String email;
  private String password;

  @Enumerated(EnumType.STRING)
  private Role role;

  private BigDecimal balance;

  public GreenbayUser() {}

  public GreenbayUser(String username, String email, String password, Role role) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.role = role;
    this.balance = BigDecimal.valueOf(0);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
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
    return true;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    GreenbayUser user = (GreenbayUser) o;
    return Objects.equals(id, user.id)
        && Objects.equals(username, user.username)
        && Objects.equals(email, user.email)
        && Objects.equals(password, user.password)
        && role == user.role
        && Objects.equals(balance, user.balance);
  }

  @Override
  public int hashCode() {
    return 0;
  }
}
