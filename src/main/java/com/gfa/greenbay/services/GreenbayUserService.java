package com.gfa.greenbay.services;

import com.gfa.greenbay.dtos.UserAuthenticationResponseDto;
import com.gfa.greenbay.dtos.UserLoginRequestDto;
import com.gfa.greenbay.dtos.UserRegisterRequestDto;

public interface GreenbayUserService {
  UserAuthenticationResponseDto register(UserRegisterRequestDto requestDto);

  UserAuthenticationResponseDto login(UserLoginRequestDto requestDto);
}
