package com.gfa.greenbay.exceptions;

import com.gfa.greenbay.dtos.MessageDto;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GreenbayExceptionHandler {
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<MessageDto> handleAuthValidationException(
      MethodArgumentNotValidException ex) {
    StringBuilder responseMessage = new StringBuilder();
    String exceptionMessage = ex.getMessage();

    List<String> exceptionMessages =
        Arrays.asList(
            "Username can not be empty.",
            "Email can not be empty.",
            "Email has bad format.",
            "Password can not be empty.",
            "Password needs to be at least 8 characters long.");

    for (String currentMessage : exceptionMessages) {
      if (exceptionMessage.contains(currentMessage)) {
        responseMessage.append(currentMessage).append(" ");
      }
    }

    MessageDto response = new MessageDto(responseMessage.toString());
    return ResponseEntity.badRequest().body(response);
  }

  @ExceptionHandler(BadCredentialsException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ResponseEntity<MessageDto> handleBadCredentialsException(BadCredentialsException ex) {
    return ResponseEntity.badRequest().body(new MessageDto(ex.getMessage()));
  }
}
