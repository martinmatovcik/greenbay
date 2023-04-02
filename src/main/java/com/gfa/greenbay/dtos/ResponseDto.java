package com.gfa.greenbay.dtos;

import java.util.Objects;

public class ResponseDto {
  private String message;

  public ResponseDto() {}

  public ResponseDto(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ResponseDto that = (ResponseDto) o;
    return Objects.equals(message, that.message);
  }

  @Override
  public int hashCode() {
    return 0;
  }
}
