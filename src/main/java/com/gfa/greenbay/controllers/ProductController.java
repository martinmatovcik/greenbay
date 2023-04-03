package com.gfa.greenbay.controllers;

import com.gfa.greenbay.dtos.ProductCreateResponseDto;
import com.gfa.greenbay.dtos.ProductDto;
import com.gfa.greenbay.dtos.ProductListResponseDto;
import com.gfa.greenbay.services.ProductService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {

  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @PostMapping
  public ResponseEntity<ProductCreateResponseDto> createProduct(
      @Valid @RequestBody ProductDto product) {
    return ResponseEntity.ok().body(productService.createProduct(product));
  }

  @GetMapping
  public ResponseEntity<List<ProductListResponseDto>> listProducts(
      @RequestParam(required = false, value = "page", defaultValue = "0") Integer pageNumber) {
    return ResponseEntity.ok().body(productService.listProducts(pageNumber));
  }
}
