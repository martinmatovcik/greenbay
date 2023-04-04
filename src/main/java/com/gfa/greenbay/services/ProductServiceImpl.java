package com.gfa.greenbay.services;

import com.gfa.greenbay.dtos.PlaceBidRequestDto;
import com.gfa.greenbay.dtos.ProductCreateResponseDto;
import com.gfa.greenbay.dtos.ProductDto;
import com.gfa.greenbay.dtos.ProductListResponseDto;
import com.gfa.greenbay.dtos.ProductSpecificResponseDto;
import com.gfa.greenbay.entities.Bid;
import com.gfa.greenbay.entities.GreenbayUser;
import com.gfa.greenbay.entities.Product;
import com.gfa.greenbay.exceptions.IllegalOperationException;
import com.gfa.greenbay.exceptions.NotFoundException;
import com.gfa.greenbay.repositories.BidRepository;
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
  private final BidRepository bidRepository;
  private static final Integer PAGE_SIZE = 20;

  @Autowired
  public ProductServiceImpl(
      ProductRepository productRepository,
      UserDetailsService userDetailsService,
      BidRepository bidRepository) {
    this.productRepository = productRepository;
    this.userDetailsService = userDetailsService;
    this.bidRepository = bidRepository;
  }

  @Override
  public ProductCreateResponseDto createProduct(
      ProductDto productDto, HttpServletRequest httpRequest) {
    GreenbayUser user =
        (GreenbayUser) userDetailsService.loadUserByUsername(httpRequest.getRemoteUser());
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
    return new ProductSpecificResponseDto(loadProductForId(productId));
  }

  @Override
  public Product loadProductForId(Long productId) {
    return productRepository
        .findById(productId)
        .orElseThrow(() -> new NotFoundException("Product with this id was not found."));
  }

  @Override
  public ProductSpecificResponseDto placeBid(
      PlaceBidRequestDto bidRequest, HttpServletRequest httpRequest) {
    Product product = loadProductForId(bidRequest.getProductId());

    if (product.isSold()) {
      throw new IllegalOperationException("This item can't be bought! It has been sold already.");
    }

    GreenbayUser user =
        (GreenbayUser) userDetailsService.loadUserByUsername(httpRequest.getRemoteUser());

    Integer bidValue = bidRequest.getValue();

    if (bidValue > user.getBalance()) {
      throw new IllegalOperationException("There is not enough money in your account.");
    }

    List<Bid> bids = product.getBids();
    Integer highestBid = 0;

    if (bids != null && !bids.isEmpty()){
      highestBid = bids.get(bids.size() - 1).getValue();
    }

    if (bidValue.equals(highestBid)) {
      throw new IllegalOperationException(
          "Bid needs to be higher than the previous one. Highest bid for this item is: "
              + highestBid);
    }
    if (bidValue < highestBid) {
      throw new IllegalOperationException(
          "Bid is too low. Highest bid for this item is: " + highestBid);
    }

    Bid bid = bidRepository.save(new Bid(product, user, bidValue));
    product.placeBid(bid);
    productRepository.save(product);

    return new ProductSpecificResponseDto(product);
  }
}
