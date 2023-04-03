package com.gfa.greenbay.services;

import com.gfa.greenbay.dtos.TokenResponseDto;
import com.gfa.greenbay.dtos.UserLoginRequestDto;
import com.gfa.greenbay.dtos.UserRegisterRequestDto;
import com.gfa.greenbay.entities.GreenbayUser;
import com.gfa.greenbay.enums.Role;
import com.gfa.greenbay.repositories.GreenbayUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class GreenbayUserServiceImpl implements GreenbayUserService {

  private final GreenbayUserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final UserDetailsService userDetailsService;

  @Autowired
  public GreenbayUserServiceImpl(
      GreenbayUserRepository userRepository,
      PasswordEncoder passwordEncoder,
      JwtService jwtService,
      AuthenticationManager authenticationManager,
      UserDetailsService userDetailsService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
    this.authenticationManager = authenticationManager;
    this.userDetailsService = userDetailsService;
  }

  @Override
  public TokenResponseDto register(UserRegisterRequestDto requestDto) {
    GreenbayUser user =
        new GreenbayUser(
            requestDto.getUsername(),
            requestDto.getEmail(),
            passwordEncoder.encode(requestDto.getPassword()),
            Role.USER);
    userRepository.save(user);

    String jwtToken = jwtService.generateToken(user);
    return new TokenResponseDto(jwtToken);
  }

  @Override
  public TokenResponseDto login(UserLoginRequestDto requestDto) {

    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              requestDto.getUsername(), requestDto.getPassword()));
    } catch (AuthenticationException e) {
      throw new BadCredentialsException("Username or password is not correct!");
    }

    GreenbayUser user = (GreenbayUser) userDetailsService.loadUserByUsername(requestDto.getUsername());
    String jwtToken = jwtService.generateToken(user);
    return new TokenResponseDto(jwtToken);
  }
}