package com.gfa.greenbay.exceptions;

import com.gfa.greenbay.dtos.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GreenbayExceptionHandler {
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ResponseDto> handleAuthValidationException(
      MethodArgumentNotValidException ex) {
    StringBuilder responseMessage = new StringBuilder();
    String exceptionMessage = ex.getMessage();

    if (exceptionMessage.contains("Username can not be empty.")) {
      responseMessage.append("Username can not be empty. ");
    }
    if (exceptionMessage.contains("Email can not be empty.")) {
      responseMessage.append("Email can not be empty. ");
    }
    if (exceptionMessage.contains("Email has bad format.")) {
      responseMessage.append("Email has bad format. ");
    }
    if (exceptionMessage.contains("Password can not be empty.")) {
      responseMessage.append("Password can not be empty. ");
    }
    if (exceptionMessage.contains("Password needs to be at least 8 characters long.")) {
      responseMessage.append("Password needs to be at least 8 characters long. ");
    }

    ResponseDto response = new ResponseDto(responseMessage.toString());
    return ResponseEntity.badRequest().body(response);
  }
}
