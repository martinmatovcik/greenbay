package com.gfa.greenbay.services;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.gfa.greenbay.entities.Bid;
import com.gfa.greenbay.entities.GreenbayUser;
import com.gfa.greenbay.entities.Product;
import com.gfa.greenbay.exceptions.IllegalOperationException;
import com.gfa.greenbay.exceptions.NotFoundException;
import com.gfa.greenbay.repositories.BidRepository;
import com.gfa.greenbay.repositories.ProductRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;

@SpringBootTest
public class ProductServiceTest {

  @Autowired ProductServiceImpl productService;

  @MockBean ProductRepository productRepository;
  @MockBean UserDetailsService userDetailsService;
  @MockBean BidRepository bidRepository;
  Product inputProduct;

  @BeforeEach
  public void setup() {
    this.inputProduct =
        new Product(
            "Rolex Submariner",
            "Classic luxury diving watch",
            "https://example.com/rolex-submariner.jpg",
            1000,
            5000);
  }

  @Test
  public void createProduct_successful() {
    // Given
    Product savedProduct = inputProduct.copy();
    savedProduct.setId(1L);

    when(productRepository.save(inputProduct)).thenReturn(savedProduct);

    // When
    Product createdProduct = productService.createProduct(inputProduct);

    // Then
    assertEquals(1L, createdProduct.getId());
    assertEquals(inputProduct.getName(), createdProduct.getName());
    assertEquals(inputProduct.getDescription(), createdProduct.getDescription());
    assertEquals(inputProduct.getPhotoUrl(), createdProduct.getPhotoUrl());
    assertEquals(inputProduct.getStartingPrice(), createdProduct.getStartingPrice());
    assertEquals(inputProduct.getPurchasePrice(), createdProduct.getPurchasePrice());
  }

  @Test
  public void placeBid_productIsSold_throwsException() {
    // Given
    inputProduct.sold();
    Bid inputBid = new Bid(inputProduct, new GreenbayUser(), 10);

    // When
    Exception exception =
        assertThrows(IllegalOperationException.class, () -> productService.placeBid(inputBid));

    // Then
    assertThat(exception.getMessage())
        .contains("This item can't be bought! It has been sold already.");
  }

  @Test
  public void placeBid_userDoesntHaveEnoughMoney_throwsException() {
    // Given
    GreenbayUser inputUser = new GreenbayUser();
    Bid inputBid = new Bid(inputProduct, inputUser, 10);

    // When
    Exception exception =
        assertThrows(IllegalOperationException.class, () -> productService.placeBid(inputBid));

    // Then
    assertThat(exception.getMessage()).contains("There is not enough money in your account.");
  }

  @Test
  public void placeBid_bidValueAndActualHighestBidValueEquals_throwsException() {
    // Given
    inputProduct.setBids(
        new ArrayList<>(
            Arrays.asList(
                new Bid(inputProduct, new GreenbayUser(), 10),
                new Bid(inputProduct, new GreenbayUser(), 20))));

    GreenbayUser bidder = new GreenbayUser();
    bidder.setBalance(100);
    Bid inputBid = new Bid(inputProduct, bidder, 20);

    // When
    Exception exception =
        assertThrows(IllegalOperationException.class, () -> productService.placeBid(inputBid));

    // Then
    assertThat(exception.getMessage())
        .contains(
            "Bid needs to be higher than the previous one. Highest bid for this item is: " + 20);
  }

  @Test
  public void placeBid_bidValueIsLowerThanActualHighestBidValue_throwsException() {
    // Given
    inputProduct.setBids(
        new ArrayList<>(
            Arrays.asList(
                new Bid(inputProduct, new GreenbayUser(), 10),
                new Bid(inputProduct, new GreenbayUser(), 20))));

    GreenbayUser bidder = new GreenbayUser();
    bidder.setBalance(100);
    Bid inputBid = new Bid(inputProduct, bidder, 15);

    // When
    Exception exception =
        assertThrows(IllegalOperationException.class, () -> productService.placeBid(inputBid));

    // Then
    assertThat(exception.getMessage())
        .contains(
            "Bid needs to be higher than the previous one. Highest bid for this item is: " + 20);
  }

  @Test
  public void placeFirstBid_successful() {
    // Given
    GreenbayUser user = new GreenbayUser();
    user.setBalance(100);

    Bid inputBid = new Bid();
    inputBid.setProduct(inputProduct);
    inputBid.setUser(user);
    inputBid.setValue(30);

    Product expectedProduct = inputProduct.copy();
    expectedProduct.placeBid(inputBid);
    when(productRepository.findById(any())).thenReturn(Optional.of(expectedProduct));

    // When
    Product actualProduct = productService.placeBid(inputBid);

    // Then
    assertNotNull(actualProduct.getBids());
    List<Bid> bids = actualProduct.getBids();

    assertFalse(bids.isEmpty());
    assertEquals(inputBid.getValue(), bids.get(bids.size() - 1).getValue());
  }

  @Test
  public void placeNthBid_successful() {
    // Given
    inputProduct.setBids(
        new ArrayList<>(
            Arrays.asList(
                new Bid(inputProduct, new GreenbayUser(), 10),
                new Bid(inputProduct, new GreenbayUser(), 20))));

    GreenbayUser user = new GreenbayUser();
    user.setBalance(100);

    Bid inputBid = new Bid();
    inputBid.setProduct(inputProduct);
    inputBid.setUser(user);
    inputBid.setValue(30);

    Product expectedProduct = inputProduct.copy();
    expectedProduct.placeBid(inputBid);
    when(productRepository.findById(any())).thenReturn(Optional.of(expectedProduct));

    // When
    Product actualProduct = productService.placeBid(inputBid);

    // Then
    assertNotNull(actualProduct.getBids());
    List<Bid> bids = actualProduct.getBids();

    assertFalse(bids.isEmpty());
    assertEquals(inputBid.getValue(), bids.get(bids.size() - 1).getValue());
  }

  @Test
  public void placeBid_productWasNotFound_throwsException() {
    // Given
    GreenbayUser bidder = new GreenbayUser();
    bidder.setBalance(100);

    Bid inputBid = new Bid(inputProduct, bidder, 15);

    when(productRepository.findById(any())).thenThrow(new NotFoundException("Product was not found."));

    // When
    Exception exception =
        assertThrows(NotFoundException.class, () -> productService.placeBid(inputBid));

    // Then
    assertThat(exception.getMessage()).contains("Product was not found.");
  }
}
