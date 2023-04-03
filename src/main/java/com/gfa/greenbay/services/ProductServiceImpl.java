package com.gfa.greenbay.services;

import com.gfa.greenbay.dtos.ProductDto;
import com.gfa.greenbay.dtos.ProductResponseDto;
import com.gfa.greenbay.entities.Product;
import com.gfa.greenbay.repositories.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
  private final ProductRepository productRepository;

  public ProductServiceImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public ProductResponseDto createProduct(ProductDto productDto) {
    Product product = productRepository.save(new Product(productDto));
    return new ProductResponseDto(product);
  }
}
