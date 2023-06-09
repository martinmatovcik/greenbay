package com.gfa.greenbay.services;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.gfa.greenbay.dtos.UserLoginRequestDto;
import com.gfa.greenbay.dtos.UserRegisterRequestDto;
import com.gfa.greenbay.entities.GreenbayUser;
import com.gfa.greenbay.entities.enums.Role;
import com.gfa.greenbay.exceptions.NotUniqueException;
import com.gfa.greenbay.repositories.GreenbayUserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class GreenbayUserServiceTest {

  @Autowired GreenbayUserServiceImpl userService;
  @Autowired JwtService jwtService;

  @MockBean GreenbayUserRepository userRepository;
  @MockBean UserDetailsService userDetailsService;
  @MockBean AuthenticationManager authenticationManager;
  @MockBean PasswordEncoder passwordEncoder;

  @Test
  public void register_successful() {
    // Given
    UserRegisterRequestDto requestDto =
        new UserRegisterRequestDto("username", "username@email.com", "password");

    // When
    String actualToken = userService.register(requestDto.toUser());

    // Then
    assertEquals("username", jwtService.extractUsername(actualToken));
  }

  @Test
  public void register_usernameIsTaken_throwsNotUniqueException() {
    // Given
    UserRegisterRequestDto requestDto =
        new UserRegisterRequestDto("username", "username@email.com", "password");

    when(userRepository.findByUsername("username")).thenReturn(Optional.of(new GreenbayUser()));

    // When
    Exception exception =
        assertThrows(NotUniqueException.class, () -> userService.register(requestDto.toUser()));

    // Then
    assertThat(exception.getMessage()).contains("Username is taken!");
  }

  @Test
  public void login_successful() {
    // Given
    UserLoginRequestDto requestDto = new UserLoginRequestDto("username", "password");

    GreenbayUser user =
        new GreenbayUser("username", "user@email.com", "password", Role.USER);
    
    when(userDetailsService.loadUserByUsername("username")).thenReturn(user);

    // When
    String actualToken = userService.login(requestDto.getUsername(), requestDto.getPassword());

    // Then
    assertEquals("username", jwtService.extractUsername(actualToken));
  }

  @Test
  public void login_badCredentials_throwsBadCredentialsException() {
    // Given
    UserLoginRequestDto requestDto = new UserLoginRequestDto("username", "password");

    when(authenticationManager.authenticate(any())).thenThrow(BadCredentialsException.class);

    // When
    Exception exception =
        assertThrows(
            BadCredentialsException.class,
            () -> userService.login(requestDto.getUsername(), requestDto.getPassword()));

    // Then
    assertThat(exception.getMessage()).contains("Username or password is not correct!");
  }
}
