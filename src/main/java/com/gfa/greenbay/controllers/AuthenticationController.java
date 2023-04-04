package com.gfa.greenbay.controllers;

import com.gfa.greenbay.dtos.TokenResponseDto;
import com.gfa.greenbay.dtos.UserLoginRequestDto;
import com.gfa.greenbay.dtos.UserRegisterRequestDto;
import com.gfa.greenbay.services.GreenbayUserService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
  private final GreenbayUserService userService;

  @Autowired
  public AuthenticationController(GreenbayUserService userService) {
    this.userService = userService;
  }

  @PostMapping("/register")
  public ResponseEntity<TokenResponseDto> register(
      @Valid @RequestBody UserRegisterRequestDto requestDto) {
    return new ResponseEntity<>(userService.register(requestDto), HttpStatus.CREATED);
  }

  @PostMapping("/login")
  public ResponseEntity<TokenResponseDto> login(
      @Valid @RequestBody UserLoginRequestDto requestDto) {
    return new ResponseEntity<>(userService.login(requestDto), HttpStatus.OK);
  }
}
