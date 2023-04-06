package com.gfa.greenbay.controllers;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gfa.greenbay.entities.GreenbayUser;
import com.gfa.greenbay.exceptions.GreenbayExceptionHandler;
import com.gfa.greenbay.exceptions.NotUniqueException;
import com.gfa.greenbay.repositories.GreenbayUserRepository;
import com.gfa.greenbay.services.GreenbayUserServiceImpl;
import com.gfa.greenbay.services.JwtServiceImpl;
import java.util.Objects;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

@SpringBootTest
public class AuthenticationControllerTest {

  @Autowired AuthenticationController authenticationController;
  @Autowired GreenbayUserServiceImpl userService;
  @Autowired GreenbayExceptionHandler exceptionHandler;

  @MockBean JwtServiceImpl jwtService;
  @MockBean GreenbayUserRepository userRepository;
  @MockBean AuthenticationManager authenticationManager;
  MockMvc mockMvc;
  ObjectMapper objectMapper = new ObjectMapper();
  String loginJSON;
  String registerJSON;

  @BeforeEach
  public void setup() {
    this.mockMvc =
        MockMvcBuilders.standaloneSetup(authenticationController)
            .setControllerAdvice(new GreenbayExceptionHandler())
            .build();
    loginJSON =
        "{\n" + "    \"username\": \"test\",\n" + "    \"password\": \"password-test\"\n" + "}";
    registerJSON =
        "{\n"
            + "    \"username\": \"user\",\n"
            + "    \"email\": \"user@email.com\",\n"
            + "    \"password\": \"password\"\n"
            + "}";
  }

  @Test
  public void register_successful() throws Exception {
    // Given
    when(jwtService.generateToken(any())).thenReturn("Jwt_token");

    // When
    mockMvc
        .perform(
            post("/api/auth/register")
                .content(registerJSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON))
        // Then
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.token", is("Jwt_token")))
        .andDo(print());

    verify(userRepository, times(1)).save(any());
  }

  @Test
  public void register_usernameIsTaken_throwsNotUniqueException() throws Exception {
    // Given
    when(userRepository.findByUsername(any())).thenReturn(Optional.of(new GreenbayUser()));

    // When
    mockMvc
        .perform(
            post("/api/auth/register")
                .content(registerJSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON))
        // Then
        .andExpect(status().isConflict())
        .andExpect(
            result -> assertTrue(result.getResolvedException() instanceof NotUniqueException))
        .andExpect(jsonPath("$.statusCode", is(409)))
        .andExpect(jsonPath("$.errorDetails[0]", is("Username is taken!")));
  }

  @Test
  public void register_missingRequestBody_throwsHttpMessageNotReadableException() throws Exception {
    // Given
    registerJSON = "";

    // When
    mockMvc
        .perform(
            post("/api/auth/register")
                .content(registerJSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON))
        // Then
        .andExpect(status().isBadRequest())
        .andExpect(
            result ->
                assertTrue(
                    result.getResolvedException() instanceof HttpMessageNotReadableException))
        .andExpect(
            result ->
                assertTrue(
                    Objects.requireNonNull(result.getResolvedException())
                        .getMessage()
                        .contains("Required request body is missing")))
        .andExpect(jsonPath("$.statusCode", is(400)));
  }

  @Test
  public void register_requestBodyIsNull_throwsHttpMessageNotReadableException() throws Exception {
    // Given
    registerJSON = null;

    // When
    mockMvc
        .perform(
            post("/api/auth/register")
                .content(objectMapper.writeValueAsString(registerJSON))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON))
        // Then
        .andExpect(status().isBadRequest())
        .andExpect(
            result ->
                assertTrue(
                    result.getResolvedException() instanceof HttpMessageNotReadableException))
        .andExpect(
            result ->
                assertTrue(
                    Objects.requireNonNull(result.getResolvedException())
                        .getMessage()
                        .contains("Required request body is missing")))
        .andExpect(jsonPath("$.statusCode", is(400)));
  }

  @Test
  public void register_invalidInput_throwsMethodArgumentNotValidException() throws Exception {
    // Given
    registerJSON =
        "{\n"
            + "    \"username\": \"\",\n"
            + "    \"email\": \"notvalid.com\",\n"
            + "    \"password\": \"pass\"\n"
            + "}";

    // When
    mockMvc
        .perform(
            post("/api/auth/register")
                .content(registerJSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON))
        // Then
        .andExpect(status().isBadRequest())
        .andExpect(
            result ->
                assertTrue(
                    result.getResolvedException() instanceof MethodArgumentNotValidException))
        .andExpect(
            result ->
                assertTrue(
                    Objects.requireNonNull(result.getResolvedException())
                        .getMessage()
                        .contains("Username can not be empty.")))
        .andExpect(
            result ->
                assertTrue(
                    Objects.requireNonNull(result.getResolvedException())
                        .getMessage()
                        .contains("Email has invalid format.")))
        .andExpect(
            result ->
                assertTrue(
                    Objects.requireNonNull(result.getResolvedException())
                        .getMessage()
                        .contains("Password needs to be at least 8 characters long.")))
        .andExpect(jsonPath("$.statusCode", is(400)));
  }

  @Test
  public void login_successful() throws Exception {
    // Given
    when(jwtService.generateToken(any())).thenReturn("Jwt_token");
    when(userRepository.findByUsername("test")).thenReturn(Optional.of(new GreenbayUser()));

    // When
    mockMvc
        .perform(
            post("/api/auth/login")
                .content(loginJSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON))
        // Then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token", is("Jwt_token")))
        .andDo(print());

    verify(userRepository, times(1)).findByUsername(any());
    verify(jwtService, times(1)).generateToken(any());
  }

  @Test
  public void login_badCredentials_throwsBadCredentialsException() throws Exception {
    // Given
    when(authenticationManager.authenticate(any())).thenThrow(BadCredentialsException.class);

    // When
    mockMvc
        .perform(
            post("/api/auth/login")
                .content(loginJSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON))
        // Then
        .andExpect(status().isUnauthorized())
        .andExpect(
            result -> assertTrue(result.getResolvedException() instanceof BadCredentialsException))
        .andExpect(
            result ->
                assertTrue(
                    Objects.requireNonNull(result.getResolvedException())
                        .getMessage()
                        .contains("Username or password is not correct!")))
        .andDo(print());
  }

  @Test
  public void login_invalidInput_throwsMethodArgumentNotValidException() throws Exception {
    // Given
    loginJSON = "{\n" + "    \"username\": \"\",\n" + "    \"password\": \"\"\n" + "}";

    // When
    mockMvc
        .perform(
            post("/api/auth/login")
                .content(loginJSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON))
        // Then
        .andExpect(status().isBadRequest())
        .andExpect(
            result ->
                assertTrue(
                    result.getResolvedException() instanceof MethodArgumentNotValidException))
        .andExpect(
            result ->
                assertTrue(
                    Objects.requireNonNull(result.getResolvedException())
                        .getMessage()
                        .contains("Username can not be empty.")))
        .andExpect(
            result ->
                assertTrue(
                    Objects.requireNonNull(result.getResolvedException())
                        .getMessage()
                        .contains("Password can not be empty.")))
        .andDo(print());
  }
}
