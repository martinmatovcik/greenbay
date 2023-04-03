package com.gfa.greenbay.services;

import com.gfa.greenbay.dtos.ProductDto;
import com.gfa.greenbay.dtos.ProductResponseDto;

public interface ProductService {
  ProductResponseDto createProduct(ProductDto productDto);
}
