package com.gfa.greenbay.services;

import com.gfa.greenbay.dtos.PlaceBidRequestDto;
import com.gfa.greenbay.dtos.ProductCreateResponseDto;
import com.gfa.greenbay.dtos.ProductDto;
import com.gfa.greenbay.dtos.ProductListResponseDto;
import com.gfa.greenbay.dtos.ProductSpecificResponseDto;
import com.gfa.greenbay.entities.Product;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public interface ProductService {

  ProductCreateResponseDto createProduct(ProductDto productDto, HttpServletRequest httpRequest);

  List<ProductListResponseDto> listProducts(Integer pageNumber);

  ProductSpecificResponseDto listProductForId(Long productId);

  Product loadProductForId(Long productId);

  ProductSpecificResponseDto placeBid(PlaceBidRequestDto bidRequest, HttpServletRequest httpRequest);
}
