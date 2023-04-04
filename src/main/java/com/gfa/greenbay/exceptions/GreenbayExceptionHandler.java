package com.gfa.greenbay.exceptions;

import com.gfa.greenbay.dtos.ErrorDto;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
        new ErrorDto(403, Collections.singletonList(ex.getMessage())),
        HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(NotUniqueException.class)
  public ResponseEntity<ErrorDto> handleNotUnique(NotUniqueException ex) {
    return new ResponseEntity<>(
        new ErrorDto(409, Collections.singletonList(ex.getMessage())),
        ex.getStatus());
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorDto> handleMissingRequestBody(HttpMessageNotReadableException ex) {
    return new ResponseEntity<>(
        new ErrorDto(400, Collections.singletonList(ex.getMessage())), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorDto> handleIllegalArgument(IllegalArgumentException ex) {
    return new ResponseEntity<>(
        new ErrorDto(400, Collections.singletonList(ex.getMessage())), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(IllegalOperationException.class)
  public ResponseEntity<ErrorDto> handleIllegalOperation(IllegalOperationException ex) {
    return new ResponseEntity<>(
        new ErrorDto(400, Collections.singletonList(ex.getMessage())), ex.getStatus());
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorDto> handleNotFound(NotFoundException ex) {
    return new ResponseEntity<>(
        new ErrorDto(404, Collections.singletonList(ex.getMessage())), ex.getStatus());
  }
}
