package com.gfa.greenbay.dtos;

import java.util.Objects;

public class TokenResponseDto {
  private String token;

  public TokenResponseDto() {}

  public TokenResponseDto(String token) {
    this.token = token;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TokenResponseDto that = (TokenResponseDto) o;
    return Objects.equals(token, that.token);
  }

  @Override
  public int hashCode() {
    return 0;
  }
}
