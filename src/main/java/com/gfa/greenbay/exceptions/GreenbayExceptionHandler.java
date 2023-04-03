package com.gfa.greenbay.exceptions;

import com.gfa.greenbay.dtos.ErrorDto;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GreenbayExceptionHandler {
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorDto> handleAuthValidationException(
      MethodArgumentNotValidException ex) {

    List<FieldError> errors = ex.getBindingResult().getFieldErrors();
    List<String> errorDetails = new ArrayList<>();

    for (FieldError error : errors) {
        errorDetails.add(error.getDefaultMessage());
    }

    return new ResponseEntity<>(
        new ErrorDto(400, Collections.singletonList(errorDetails)),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ErrorDto> handleBadCredentialsException(BadCredentialsException ex) {
    return new ResponseEntity<>(
        new ErrorDto(403, Collections.singletonList(ex.getMessage())), HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<ErrorDto> handleNotUniqueUsername(DataIntegrityViolationException ex) {
    return new ResponseEntity<>(new ErrorDto(400, Collections.singletonList("Username is taken!")), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorDto> handleMissingRequestBody(HttpMessageNotReadableException ex) {
    return new ResponseEntity<>(new ErrorDto(400, Collections.singletonList(ex.getMessage())), HttpStatus.BAD_REQUEST);
  }
}
