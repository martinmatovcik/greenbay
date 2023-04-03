package com.gfa.greenbay.dtos;

import java.util.List;
import java.util.Objects;

public class ErrorDto {
  private Integer statusCode;
  private List<Object> errorDetails;

  public ErrorDto() {}

  public ErrorDto(Integer statusCode, List<Object> errorDetails) {
    this.statusCode = statusCode;
    this.errorDetails = errorDetails;
  }

  public Integer getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(Integer statusCode) {
    this.statusCode = statusCode;
  }

  public List<Object> getErrorDetails() {
    return errorDetails;
  }

  public void setErrorDetails(List<Object> errorDetails) {
    this.errorDetails = errorDetails;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ErrorDto errorDto = (ErrorDto) o;
    return Objects.equals(statusCode, errorDto.statusCode)
        && Objects.equals(errorDetails, errorDto.errorDetails);
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public String toString() {
    return "ErrorDto{" + "statusCode=" + statusCode + ", errorDetails=" + errorDetails + '}';
  }
}
