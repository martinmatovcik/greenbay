package com.gfa.greenbay.services;

import com.gfa.greenbay.entities.Bid;
import com.gfa.greenbay.entities.GreenbayUser;
import com.gfa.greenbay.entities.Product;
import com.gfa.greenbay.exceptions.IllegalOperationException;
import com.gfa.greenbay.exceptions.NotFoundException;
import com.gfa.greenbay.repositories.BidRepository;
import com.gfa.greenbay.repositories.ProductRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final BidRepository bidRepository;
  private static final Integer PAGE_SIZE = 20;
  private final MessageService messageService;

  @Autowired
  public ProductServiceImpl(
      ProductRepository productRepository,
      BidRepository bidRepository,
      MessageService messageService) {
    this.productRepository = productRepository;
    this.bidRepository = bidRepository;
    this.messageService = messageService;
  }

  @Override
  public Product createProduct(Product product) {
    return productRepository.save(product);
  }

  @Override
  public Page<Product> listProducts(Integer pageNumber) {
    Pageable pageable = PageRequest.of(pageNumber, PAGE_SIZE);
    return productRepository.findAllBySoldFalse(pageable);
  }

  @Override
  public Product loadProductForId(Long productId) {
    return productRepository
        .findById(productId)
        .orElseThrow(
            () -> new NotFoundException(messageService.getMessage("product_was_not_found_by_id")));
  }

  @Override
  public Product placeBid(Bid bid) {
    Product product = bid.getProduct();
    GreenbayUser user = bid.getUser();
    Integer bidValue = bid.getValue();

    if (product.isSold()) {
      throw new IllegalOperationException(
          messageService.getMessage("product_has_been_sold_already"));
    }

    if (bidValue > user.getBalance()) {
      throw new IllegalOperationException(messageService.getMessage("not_enough_user_balance"));
    }

    List<Bid> bids = product.getBids();

    if (bids.isEmpty() && bidValue < product.getStartingPrice()) {
      throw new IllegalOperationException(
          messageService.getMessage("bid_is_lower_than_starting_price"));
    }

    Integer highestBid = 0;
    if (!bids.isEmpty()) {
      highestBid = bids.get(bids.size() - 1).getValue();
    }

    if (bidValue <= highestBid) {
      throw new IllegalOperationException(
          messageService.getMessage("bid_is_lower_than_previous_bid") + highestBid);
    }

    bidRepository.save(bid);

    if (bidValue >= product.getPurchasePrice()) {
      product.setBuyer(user);
      productRepository.save(product);
    }

    return productRepository
        .findById(product.getId())
        .orElseThrow(() -> new NotFoundException(messageService.getMessage("product_was_not_found_by_id")));
  }

  @Override
  public void deleteProduct(Long productId) {
    productRepository
        .findById(productId)
        .orElseThrow(() -> new NotFoundException(messageService.getMessage("product_was_not_found_by_id")));
    productRepository.deleteById(productId);
  }

  @Override
  public void deleteBid(Long bidId) {
    bidRepository
        .findById(bidId)
        .orElseThrow(() -> new NotFoundException(messageService.getMessage("bid_was_not_found")));
    bidRepository.deleteById(bidId);
  }
}
