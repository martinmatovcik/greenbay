package com.gfa.greenbay.dtos;

import com.gfa.greenbay.entities.GreenbayUser;
import com.gfa.greenbay.entities.enums.Role;
import java.util.Objects;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class UserRegisterRequestDto {
  @NotBlank(message = "Username can not be empty.")
  private String username;

  @NotBlank(message = "Email can not be empty.")
  @Email(message = "Email has invalid format.")
  private String email;

  @NotBlank(message = "Password can not be empty.")
  @Pattern(message = "Password needs to be at least 8 characters long.", regexp = "^.{8,}$")
  private String password;

  public UserRegisterRequestDto() {}

  public UserRegisterRequestDto(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
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
    UserRegisterRequestDto that = (UserRegisterRequestDto) o;
    return Objects.equals(username, that.username)
        && Objects.equals(email, that.email)
        && Objects.equals(password, that.password);
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public GreenbayUser toUser() {
    return new GreenbayUser(this.username, this.email, this.password, Role.USER);
  }
}
