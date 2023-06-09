package com.gfa.greenbay.dtos;

import java.util.Objects;
import javax.validation.constraints.NotBlank;

public class UserLoginRequestDto {
  @NotBlank(message = "username_cannot_be_empty")
  private String username;

  @NotBlank(message = "password_cannot_be_empty")
  private String password;

  public UserLoginRequestDto() {}

  public UserLoginRequestDto(String username, String password) {
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
    UserLoginRequestDto that = (UserLoginRequestDto) o;
    return Objects.equals(username, that.username) && Objects.equals(password, that.password);
  }

  @Override
  public int hashCode() {
    return 0;
  }
}
