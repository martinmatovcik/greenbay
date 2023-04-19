package com.gfa.greenbay.controllers;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gfa.greenbay.entities.GreenbayUser;
import com.gfa.greenbay.entities.Product;
import com.gfa.greenbay.entities.enums.Role;
import com.gfa.greenbay.repositories.BidRepository;
import com.gfa.greenbay.repositories.GreenbayUserRepository;
import com.gfa.greenbay.repositories.ProductRepository;
import com.gfa.greenbay.services.JwtService;
import com.gfa.greenbay.services.ProductServiceImpl;
import java.util.Objects;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
//@AutoConfigureMockMvc
public class ProductControllerTest {

  @Autowired ProductController productController;
  @Autowired ProductServiceImpl productService;
  @Autowired UserDetailsService userDetailsService;
  @Autowired WebApplicationContext context;

  @MockBean JwtService jwtService;
  @MockBean GreenbayUserRepository userRepository;
  @MockBean ProductRepository productRepository;
  @MockBean BidRepository bidRepository;

  MockMvc mockMvc;
  ObjectMapper objectMapper = new ObjectMapper();

  @BeforeEach
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
  }

  @Test
  public void createProduct_successful() throws Exception {
    // Given
    String productJSON =
        "{\n"
            + "    \"name\": \"Hockey stick\",\n"
            + "    \"description\": \"Wooden stick, signed by SLAFKOVSKY\",\n"
            + "    \"photoUrl\": \"https://m.media-amazon.com/images/W/IMAGERENDERING_521856-T1/images/I/81h+Nphld6L._SL1500_.jpg\",\n"
            + "    \"startingPrice\": \"950\",\n"
            + "    \"purchasePrice\": \"2200\"\n"
            + "}";
    useUserWithRole(Role.USER);

    String name = "Hockey stick";
    String description = "Wooden stick, signed by SLAFKOVSKY";
    String photoUrl =
        "https://m.media-amazon.com/images/W/IMAGERENDERING_521856-T1/images/I/81h+Nphld6L._SL1500_.jpg";

    Product savedProduct = new Product(name, description, photoUrl, 950, 2200);
    savedProduct.setId(1L);

    when(productRepository.save(any())).thenReturn(savedProduct);

    // When
    mockMvc
        .perform(
            post("/api/products")
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .content(productJSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON))
        // Then
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.name", is(name)))
        .andExpect(jsonPath("$.description", is(description)))
        .andExpect(jsonPath("$.photoUrl", is(photoUrl)))
        .andExpect(jsonPath("$.startingPrice", is(950)))
        .andExpect(jsonPath("$.purchasePrice", is(2200)))
        .andExpect(jsonPath("$.id").isNotEmpty())
        .andDo(print());
  }

  @Test
  public void createProduct_missingRequestBody_throwsHttpMessageNotReadableException()
      throws Exception {
    // Given
    String productJSON = "";
    useUserWithRole(Role.USER);

    // When
    mockMvc
        .perform(
            post("/api/products")
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .content(productJSON)
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
  public void createProduct_requestBodyIsNull_throwsHttpMessageNotReadableException()
      throws Exception {
    // Given
    String productJSON = null;
    useUserWithRole(Role.USER);

    // When
    mockMvc
        .perform(
            post("/api/products")
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .content(objectMapper.writeValueAsString(productJSON))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON))
        // Then
        .andExpect(status().isBadRequest())
        .andExpect(
            result ->
                assertTrue(
                    result.getResolvedException() instanceof HttpMessageNotReadableException
                        && Objects.requireNonNull(result.getResolvedException())
                            .getMessage()
                            .contains("Required request body is missing")))
        .andExpect(jsonPath("$.statusCode", is(400)));
  }

  @Test
  public void createProduct_invalidInput_throwsMethodArgumentNotValidException() throws Exception {
    // Given
    String productJSON =
        "{\n"
            + "    \"name\": \"\",\n"
            + "    \"description\": \"\",\n"
            + "    \"photoUrl\": \"image.com\",\n"
            + "    \"startingPrice\": \"\",\n"
            + "    \"purchasePrice\": \"-150\"\n"
            + "}";
    useUserWithRole(Role.USER);
    // When
    mockMvc
        .perform(
            post("/api/products")
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .content(productJSON)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON))
        // Then
        .andExpect(status().isBadRequest())
        .andExpect(
            result ->
                assertTrue(
                    result.getResolvedException() instanceof MethodArgumentNotValidException
                        && Objects.requireNonNull(result.getResolvedException())
                            .getMessage()
                            .contains("Name can not be empty.")
                        && result
                            .getResolvedException()
                            .getMessage()
                            .contains("Description can not be empty.")
                        && result
                            .getResolvedException()
                            .getMessage()
                            .contains("Please provide valid URL.")
                        && result
                            .getResolvedException()
                            .getMessage()
                            .contains("Starting price can not be empty.")
                        && result
                            .getResolvedException()
                            .getMessage()
                            .contains("Purchase price needs to be positive integer.")))
        .andExpect(jsonPath("$.statusCode", is(400)));
  }

  @Test
  public void deleteProduct_successful_forAdminRole() throws Exception {
    // Given
    useUserWithRole(Role.ADMIN);
    when(productRepository.findById(any())).thenReturn(Optional.of(new Product()));

    // When
    mockMvc
        .perform(
            delete("/api/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON))
        // Then
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message", is("Successfully deleted.")));
  }

  @Test
  public void deleteProduct_forbidden_forUserRole() throws Exception {
    // Given
    useUserWithRole(Role.USER);

    when(productRepository.findById(any())).thenReturn(Optional.of(new Product()));

    // When
    mockMvc
        .perform(
            delete("/api/products/1")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer token")
                .characterEncoding("UTF-8")
                .accept(MediaType.APPLICATION_JSON))
        // Then
        .andExpect(status().isForbidden());
  }

  private void useUserWithRole(Role role) {
    GreenbayUser admin = new GreenbayUser();
    admin.setUsername(role.name());
    admin.setRole(role);

    when(userRepository.findByUsername(any())).thenReturn(Optional.of(admin));
    when(jwtService.extractUsername(any())).thenReturn(admin.getUsername());
    when(jwtService.isTokenValidForUsername(any(), any())).thenReturn(true);
  }
}
