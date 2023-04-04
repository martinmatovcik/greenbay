package com.gfa.greenbay.services;

import com.gfa.greenbay.dtos.ProductCreateResponseDto;
import com.gfa.greenbay.dtos.ProductDto;
import com.gfa.greenbay.dtos.ProductListResponseDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ProductService {

    ProductCreateResponseDto createProduct(ProductDto productDto, HttpServletRequest request);

    List<ProductListResponseDto> listProducts(Integer pageNumber);
}
