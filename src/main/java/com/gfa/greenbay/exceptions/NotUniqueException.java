package com.gfa.greenbay.exceptions;

import org.springframework.http.HttpStatus;

public class NotUniqueException extends RuntimeException {
  private final HttpStatus status;

  public NotUniqueException(String errorMessage) {
    super(errorMessage);
    this.status = HttpStatus.CONFLICT;
  }

  public HttpStatus getStatus() {
    return status;
  }
}
