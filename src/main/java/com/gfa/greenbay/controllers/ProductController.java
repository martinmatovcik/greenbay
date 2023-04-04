package com.gfa.greenbay.controllers;

import com.gfa.greenbay.dtos.PlaceBidRequestDto;
import com.gfa.greenbay.dtos.ProductCreateResponseDto;
import com.gfa.greenbay.dtos.ProductDto;
import com.gfa.greenbay.dtos.ProductListResponseDto;
import com.gfa.greenbay.dtos.ProductSpecificResponseDto;
import com.gfa.greenbay.services.ProductService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

  @PostMapping
  public ResponseEntity<ProductCreateResponseDto> createProduct(
      @Valid @RequestBody ProductDto product, HttpServletRequest httpRequest) {
    return new ResponseEntity<>(
        productService.createProduct(product, httpRequest), HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<ProductListResponseDto>> listProducts(
      @RequestParam(required = false, value = "page", defaultValue = "0") Integer pageNumber) {
    return new ResponseEntity<>(productService.listProducts(pageNumber), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductSpecificResponseDto> listSpecificProduct(
      @PathVariable("id") Long productId) {
    return new ResponseEntity<>(productService.listProductForId(productId), HttpStatus.OK);
  }

  @PostMapping("/bid")
  public ResponseEntity<ProductSpecificResponseDto> placeBid(@RequestBody PlaceBidRequestDto bidRequest, HttpServletRequest httpRequest) {
      return new ResponseEntity<>(productService.placeBid(bidRequest, httpRequest), HttpStatus.CREATED);
  }
}
