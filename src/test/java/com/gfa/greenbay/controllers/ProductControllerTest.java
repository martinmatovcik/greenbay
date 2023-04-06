package com.gfa.greenbay.controllers;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gfa.greenbay.entities.GreenbayUser;
import com.gfa.greenbay.entities.Product;
import com.gfa.greenbay.exceptions.GreenbayExceptionHandler;
import com.gfa.greenbay.repositories.BidRepository;
import com.gfa.greenbay.repositories.GreenbayUserRepository;
import com.gfa.greenbay.repositories.ProductRepository;
import com.gfa.greenbay.services.ProductServiceImpl;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
public class ProductControllerTest {

  @Autowired ProductController productController;
  @Autowired ProductServiceImpl productService;
  @Autowired UserDetailsService userDetailsService;

  @MockBean GreenbayUserRepository userRepository;
  @MockBean ProductRepository productRepository;
  @MockBean BidRepository bidRepository;
  @Mock MockHttpServletRequest mockedHttpRequest;

  MockMvc mockMvc;
  ObjectMapper objectMapper = new ObjectMapper();

  @BeforeEach
  public void setup() {
    this.mockMvc =
        MockMvcBuilders.standaloneSetup(productController)
            .setControllerAdvice(new GreenbayExceptionHandler())
            .build();
  }

  @Test
  public void createProduct_successful() throws Exception {
    // Given
    String name = "Hockey stick";
    String description = "Wooden stick, signed by SLAFKOVSKY";
    String photoUrl =
        "https://m.media-amazon.com/images/W/IMAGERENDERING_521856-T1/images/I/81h+Nphld6L._SL1500_.jpg";

        // Exact JSON input as in postman
    String productJSON =
        "{\n"
            + "    \"name\": \"Hockey stick\",\n"
            + "    \"description\": \"Wooden stick, signed by SLAFKOVSKY\",\n"
            + "    \"photoUrl\": \"https://m.media-amazon.com/images/W/IMAGERENDERING_521856-T1/images/I/81h+Nphld6L._SL1500_.jpg\",\n"
            + "    \"startingPrice\": \"950\",\n"
            + "    \"purchasePrice\": \"2200\"\n"
            + "}";


    Product savedProduct = new Product(name, description, photoUrl, 950, 2200);
    savedProduct.setId(1L);

    when(userRepository.findByUsername(any())).thenReturn(Optional.of(new GreenbayUser()));
    when(mockedHttpRequest.getRemoteUser()).thenReturn("username");
    when(productRepository.save(any())).thenReturn(savedProduct);

    // When
    mockMvc
        .perform(
            post("/api/products")
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
}
