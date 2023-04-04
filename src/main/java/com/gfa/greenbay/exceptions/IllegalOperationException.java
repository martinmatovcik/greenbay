package com.gfa.greenbay.exceptions;

import org.springframework.http.HttpStatus;

public class IllegalOperationException extends RuntimeException {
  private final HttpStatus status;
  public IllegalOperationException(String errorMessage) {
    super(errorMessage);
    this.status = HttpStatus.BAD_REQUEST;
  }

  public HttpStatus getStatus() {
    return status;
  }
}
