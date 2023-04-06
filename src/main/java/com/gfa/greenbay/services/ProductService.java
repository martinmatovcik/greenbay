package com.gfa.greenbay.services;

import com.gfa.greenbay.entities.Bid;
import com.gfa.greenbay.entities.Product;
import org.springframework.data.domain.Page;

public interface ProductService {

  Product createProduct(Product product);

  Page<Product> listProducts(Integer pageNumber);

  Product loadProductForId(Long productId);

  Product placeBid(Bid bid);

  void deleteProduct(Long productId);

  void deleteBid(Long bidId);
}
