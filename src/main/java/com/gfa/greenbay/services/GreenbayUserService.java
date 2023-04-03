package com.gfa.greenbay.services;

import com.gfa.greenbay.dtos.TokenResponseDto;
import com.gfa.greenbay.dtos.UserLoginRequestDto;
import com.gfa.greenbay.dtos.UserRegisterRequestDto;

public interface GreenbayUserService {
  TokenResponseDto register(UserRegisterRequestDto requestDto);

  TokenResponseDto login(UserLoginRequestDto requestDto);
}
