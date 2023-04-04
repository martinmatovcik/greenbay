package com.gfa.greenbay.controllers;

import com.gfa.greenbay.dtos.ProductCreateResponseDto;
import com.gfa.greenbay.dtos.ProductDto;
import com.gfa.greenbay.dtos.ProductListResponseDto;
import com.gfa.greenbay.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductCreateResponseDto> createProduct(
            @Valid @RequestBody ProductDto product, HttpServletRequest request) {
        return ResponseEntity.ok().body(productService.createProduct(product, request));
    }

    @GetMapping
    public ResponseEntity<List<ProductListResponseDto>> listProducts(
            @RequestParam(required = false, value = "page", defaultValue = "0") Integer pageNumber) {
        return ResponseEntity.ok().body(productService.listProducts(pageNumber));
    }
}
