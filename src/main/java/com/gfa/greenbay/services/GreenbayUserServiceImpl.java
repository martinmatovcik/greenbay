package com.gfa.greenbay.services;

import com.gfa.greenbay.entities.GreenbayUser;
import com.gfa.greenbay.exceptions.NotUniqueException;
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
      AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
    this.authenticationManager = authenticationManager;
    this.userDetailsService = userDetailsService;
  }

  @Override
  public String register(GreenbayUser userToRegister) {
    if (isUsernamePresent(userToRegister.getUsername())) {
      throw new NotUniqueException("Username is taken!");
    }

    GreenbayUser registeredUser =
        new GreenbayUser(
            userToRegister.getUsername(),
            userToRegister.getEmail(),
            passwordEncoder.encode(userToRegister.getPassword()),
            userToRegister.getRole());
    userRepository.save(registeredUser);

    return jwtService.generateToken(registeredUser);
  }

  @Override
  public String login(String username, String password) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              username, password));
    } catch (AuthenticationException e) {
      throw new BadCredentialsException("Username or password is not correct!");
    }

    GreenbayUser user =
        (GreenbayUser) userDetailsService.loadUserByUsername(username);
    
    return jwtService.generateToken(user);
  }

  private Boolean isUsernamePresent(String username) {
    return userRepository.findByUsername(username).isPresent();
  }
}
