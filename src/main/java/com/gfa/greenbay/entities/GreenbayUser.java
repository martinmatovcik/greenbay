package com.gfa.greenbay.entities;

import com.gfa.greenbay.entities.enums.Role;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
public class GreenbayUser implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String username;

  private String email;
  private String password;

  @Enumerated(EnumType.STRING)
  private Role role;

  private Integer balance = 0;

  @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
  private List<Product> sellableProducts = new ArrayList<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<Bid> bids = new ArrayList<>();

  @OneToMany(mappedBy = "buyer", cascade = CascadeType.ALL)
  private List<Product> boughtProducts = new ArrayList<>();

  public GreenbayUser() {
  }

  public GreenbayUser(String username, String email, String password, Role role) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.role = role;
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
    this.role=role;
  }

  public void setUser() {
    this.role = Role.USER;
  }

  public void setAdmin() {
    this.role = Role.ADMIN;
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

  public Integer getBalance() {
    return balance;
  }

  public void setBalance(Integer balance) {
    this.balance = balance;
  }

  public List<Bid> getBids() {
    return bids;
  }

  public void setBids(List<Bid> bids) {
    this.bids = bids;
  }

  public List<Product> getSellableProducts() {
    return sellableProducts;
  }

  public void setSellableProducts(List<Product> sellableProducts) {
    this.sellableProducts = sellableProducts;
  }

  public List<Product> getBoughtProducts() {
    return boughtProducts;
  }

  public void setBoughtProducts(List<Product> boughtProducts) {
    this.boughtProducts = boughtProducts;
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
        && Objects.equals(balance, user.balance)
        && Objects.equals(sellableProducts, user.sellableProducts)
        && Objects.equals(bids, user.bids)
        && Objects.equals(boughtProducts, user.boughtProducts);
  }

  @Override
  public int hashCode() {
    return 0;
  }
}
