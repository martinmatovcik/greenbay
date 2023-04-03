package com.gfa.greenbay.services;

import com.gfa.greenbay.dtos.ProductCreateResponseDto;
import com.gfa.greenbay.dtos.ProductDto;
import com.gfa.greenbay.dtos.ProductListResponseDto;
import java.util.List;

public interface ProductService {

  ProductCreateResponseDto createProduct(ProductDto productDto);

  List<ProductListResponseDto> listProducts(Integer pageNumber);
}
