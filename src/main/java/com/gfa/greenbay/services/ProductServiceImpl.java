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

  @Autowired
  public ProductServiceImpl(ProductRepository productRepository, BidRepository bidRepository) {
    this.productRepository = productRepository;
    this.bidRepository = bidRepository;
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
        .orElseThrow(() -> new NotFoundException("Product with this id was not found."));
  }

  @Override
  public Product placeBid(Bid bid) {
    Product product = bid.getProduct();
    GreenbayUser user = bid.getUser();
    Integer bidValue = bid.getValue();

    if (product.isSold()) {
      throw new IllegalOperationException("This item can't be bought! It has been sold already.");
    }

    if (bidValue > user.getBalance()) {
      throw new IllegalOperationException("There is not enough money in your account.");
    }

    List<Bid> bids = product.getBids();

    if (bids.isEmpty() && bidValue < product.getStartingPrice()) {
      throw new IllegalOperationException("Your bid has to be higher than the starting price!");
    }

    Integer highestBid = 0;
    if (!bids.isEmpty()) {
      highestBid = bids.get(bids.size() - 1).getValue();
    }

    if (bidValue <= highestBid) {
      throw new IllegalOperationException(
          "Bid needs to be higher than the previous one. Highest bid for this item is: "
              + highestBid);
    }

    bidRepository.save(bid);

    if (bidValue >= product.getPurchasePrice()) {
      product.setBuyer(user);
      productRepository.save(product);
    }

    return productRepository
        .findById(product.getId())
        .orElseThrow(() -> new NotFoundException("Product was not found"));
  }

  @Override
  public void deleteProduct(Long productId) {
    productRepository
        .findById(productId)
        .orElseThrow(() -> new NotFoundException("Product was not found."));
    productRepository.deleteById(productId);
  }

  @Override
  public void deleteBid(Long bidId) {
    bidRepository.findById(bidId).orElseThrow(() -> new NotFoundException("Bid was not found."));
    bidRepository.deleteById(bidId);
  }
}
