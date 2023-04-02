package com.gfa.greenbay.services;

import com.gfa.greenbay.dtos.AuthenticationResponseDto;
import com.gfa.greenbay.dtos.LoginRequestDto;
import com.gfa.greenbay.dtos.RegisterRequestDto;
import com.gfa.greenbay.entities.GreenbayUser;
import com.gfa.greenbay.enums.Role;
import com.gfa.greenbay.repositories.GreenbayUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class GreenbayUserServiceImpl implements GreenbayUserService {

  private final GreenbayUserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  @Autowired
  public GreenbayUserServiceImpl(
      GreenbayUserRepository userRepository,
      PasswordEncoder passwordEncoder,
      JwtService jwtService,
      AuthenticationManager authenticationManager) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
    this.authenticationManager = authenticationManager;
  }

  @Override
  public AuthenticationResponseDto register(RegisterRequestDto requestDto) {
    GreenbayUser user =
        new GreenbayUser(
            requestDto.getUsername(),
            requestDto.getEmail(),
            passwordEncoder.encode(requestDto.getPassword()),
            Role.USER);
    userRepository.save(user);

    String jwtToken = jwtService.generateToken(user);
    return new AuthenticationResponseDto(jwtToken);
  }

  @Override
  public AuthenticationResponseDto login(LoginRequestDto requestDto) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            requestDto.getUsername(), requestDto.getPassword()));

    GreenbayUser user =
        userRepository
            .findByUsername(requestDto.getUsername())
            .orElseThrow(() -> new UsernameNotFoundException("User not found."));

    String jwtToken = jwtService.generateToken(user);
    return new AuthenticationResponseDto(jwtToken);
  }
}
