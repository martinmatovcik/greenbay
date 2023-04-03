package com.gfa.greenbay.services;

import com.gfa.greenbay.dtos.ProductCreateResponseDto;
import com.gfa.greenbay.dtos.ProductDto;
import com.gfa.greenbay.dtos.ProductListResponseDto;
import com.gfa.greenbay.entities.Product;
import com.gfa.greenbay.repositories.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;

  public ProductServiceImpl(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Override
  public ProductCreateResponseDto createProduct(ProductDto productDto) {
    Product product = productRepository.save(new Product(productDto));
    return new ProductCreateResponseDto(product);
  }

  @Override
  public List<ProductListResponseDto> listProducts(Integer pageNumber) {
    if (pageNumber < 0) {
      //TODO -- exception + handling
    }

    Pageable pageable = PageRequest.of(pageNumber, 20);
    Page<Product> productPage = productRepository.findAllByDeleted(false, pageable);

    List<ProductListResponseDto> responsePage = new ArrayList<>();

    for (Product product : productPage) {
      responsePage.add(new ProductListResponseDto(product));
    }

    return responsePage;
  }
}
