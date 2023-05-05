package com.gfa.greenbay.controllers;

import com.gfa.greenbay.dtos.MessageDto;
import com.gfa.greenbay.dtos.TokenResponseDto;
import com.gfa.greenbay.dtos.UserLoginRequestDto;
import com.gfa.greenbay.dtos.UserRegisterRequestDto;
import com.gfa.greenbay.entities.GreenbayUser;
import com.gfa.greenbay.services.GreenbayUserService;
import com.gfa.greenbay.services.MessageService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class UserController {
  private final GreenbayUserService userService;
  private final MessageService messageService;

  @Autowired
  public UserController(GreenbayUserService userService, MessageService messageService) {
    this.userService = userService;
    this.messageService = messageService;
  }

  @PostMapping("/register")
  public ResponseEntity<TokenResponseDto> register(
      @Valid @RequestBody UserRegisterRequestDto requestDto) {
    GreenbayUser userToRegister = requestDto.toUser();
    String token = userService.register(userToRegister);

    return new ResponseEntity<>(new TokenResponseDto(token), HttpStatus.CREATED);
  }

  @PostMapping("/login")
  public ResponseEntity<TokenResponseDto> login(
      @Valid @RequestBody UserLoginRequestDto requestDto) {

    String token = userService.login(requestDto.getUsername(), requestDto.getPassword());

    return new ResponseEntity<>(new TokenResponseDto(token), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<MessageDto> deleteBid(@PathVariable("id") Long userId){
    userService.deleteUser(userId);
    return new ResponseEntity<>(new MessageDto(messageService.getMessage("successfully_deleted")), HttpStatus.OK);
  }
}
