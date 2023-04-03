package com.gfa.greenbay.controllers;

import com.gfa.greenbay.dtos.ProductDto;
import com.gfa.greenbay.dtos.ProductResponseDto;
import com.gfa.greenbay.services.ProductService;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProductController {

  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @PostMapping("/create-product")
  public ResponseEntity<ProductResponseDto> createProduct(@Valid @RequestBody ProductDto product) {
    return ResponseEntity.ok().body(productService.createProduct(product));
  }
}
