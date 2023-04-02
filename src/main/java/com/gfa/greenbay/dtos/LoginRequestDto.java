package com.gfa.greenbay.dtos;

import java.util.Objects;
import javax.validation.constraints.NotNull;

public class LoginRequestDto {
  @NotNull(message = "Username can not be empty.")
  private String username;

  @NotNull(message = "Password can not be empty.")
  private String password;

  public LoginRequestDto() {}

  public LoginRequestDto(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    LoginRequestDto that = (LoginRequestDto) o;
    return Objects.equals(username, that.username) && Objects.equals(password, that.password);
  }

  @Override
  public int hashCode() {
    return 0;
  }
}
