package com.gfa.greenbay.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends RuntimeException {
  private final HttpStatus status;

  public NotFoundException(String errorMessage) {
    super(errorMessage);
    this.status = HttpStatus.NOT_FOUND;
  }

  public HttpStatus getStatus() {
    return status;
  }
}
