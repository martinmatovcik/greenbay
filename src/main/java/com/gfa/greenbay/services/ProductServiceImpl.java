package com.gfa.greenbay.services;

import com.gfa.greenbay.dtos.ProductCreateResponseDto;
import com.gfa.greenbay.dtos.ProductDto;
import com.gfa.greenbay.dtos.ProductListResponseDto;
import com.gfa.greenbay.dtos.ProductSpecificResponseDto;
import com.gfa.greenbay.entities.GreenbayUser;
import com.gfa.greenbay.entities.Product;
import com.gfa.greenbay.repositories.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final UserDetailsService userDetailsService;
  private static final Integer PAGE_SIZE = 20;

  @Autowired
  public ProductServiceImpl(
      ProductRepository productRepository, UserDetailsService userDetailsService) {
    this.productRepository = productRepository;
    this.userDetailsService = userDetailsService;
  }

  @Override
  public ProductCreateResponseDto createProduct(ProductDto productDto, HttpServletRequest request) {
    GreenbayUser user =
        (GreenbayUser) userDetailsService.loadUserByUsername(request.getRemoteUser());
    Product product = productRepository.save(new Product(productDto, user));
    return new ProductCreateResponseDto(product);
  }

  @Override
  public List<ProductListResponseDto> listProducts(Integer pageNumber) {
    Pageable pageable = PageRequest.of(pageNumber, PAGE_SIZE);
    Page<Product> productPage = productRepository.findAllBySoldFalse(pageable);

    List<ProductListResponseDto> responsePage = new ArrayList<>();

    for (Product product : productPage) {
      responsePage.add(new ProductListResponseDto(product));
    }

    return responsePage;
  }

  @Override
  public ProductSpecificResponseDto listProductForId(Long productId) {
    return new ProductSpecificResponseDto(
        productRepository
            .findById(productId)
            .orElseThrow(() -> new RuntimeException("Id not found."))); // TODO -- handle this ex
  }
}
