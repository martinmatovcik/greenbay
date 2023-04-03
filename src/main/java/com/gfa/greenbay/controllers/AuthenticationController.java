package com.gfa.greenbay.controllers;

import com.gfa.greenbay.dtos.AuthenticationResponseDto;
import com.gfa.greenbay.dtos.LoginRequestDto;
import com.gfa.greenbay.dtos.RegisterRequestDto;
import com.gfa.greenbay.services.GreenbayUserService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
  public ResponseEntity<AuthenticationResponseDto> register(
      @Valid @RequestBody RegisterRequestDto requestDto) {
    return ResponseEntity.ok(userService.register(requestDto));
  }

  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponseDto> login(
      @Valid @RequestBody LoginRequestDto requestDto) {
    return ResponseEntity.ok(userService.login(requestDto));
  }
}
