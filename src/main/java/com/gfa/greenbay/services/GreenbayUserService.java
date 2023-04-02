package com.gfa.greenbay.services;

import com.gfa.greenbay.dtos.AuthenticationResponseDto;
import com.gfa.greenbay.dtos.LoginRequestDto;
import com.gfa.greenbay.dtos.RegisterRequestDto;

public interface GreenbayUserService {
  AuthenticationResponseDto register(RegisterRequestDto requestDto);

  AuthenticationResponseDto login(LoginRequestDto requestDto);
}
