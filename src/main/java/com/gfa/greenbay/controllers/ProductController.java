package com.gfa.greenbay.controllers;

import com.gfa.greenbay.dtos.MessageDto;
import com.gfa.greenbay.dtos.PlaceBidRequestDto;
import com.gfa.greenbay.dtos.ProductCreateResponseDto;
import com.gfa.greenbay.dtos.ProductDto;
import com.gfa.greenbay.dtos.ProductListResponseDto;
import com.gfa.greenbay.dtos.ProductSpecificResponseDto;
import com.gfa.greenbay.entities.Bid;
import com.gfa.greenbay.entities.GreenbayUser;
import com.gfa.greenbay.entities.Product;
import com.gfa.greenbay.services.MessageService;
import com.gfa.greenbay.services.ProductService;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

  private final ProductService productService;
  private final UserDetailsService userDetailsService;
  private final MessageService messageService;

  @Autowired
  public ProductController(
      ProductService productService,
      UserDetailsService userDetailsService,
      MessageService messageService) {
    this.productService = productService;
    this.userDetailsService = userDetailsService;
    this.messageService = messageService;
  }

  @PostMapping
  public ResponseEntity<ProductCreateResponseDto> createProduct(
      @Valid @RequestBody ProductDto productDto, HttpServletRequest httpRequest) {
    GreenbayUser user =
        (GreenbayUser) userDetailsService.loadUserByUsername(httpRequest.getRemoteUser());

    Product product = productDto.toProductForUser(user);

    Product createdProduct = productService.createProduct(product);
    ProductCreateResponseDto productCreateResponseDto =
        new ProductCreateResponseDto(createdProduct);

    return new ResponseEntity<>(productCreateResponseDto, HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<ProductListResponseDto>> listProducts(
      @RequestParam(required = false, value = "page", defaultValue = "0") Integer pageNumber) {

    Page<Product> productPage = productService.listProducts(pageNumber);

    List<ProductListResponseDto> responsePage = new ArrayList<>();
    for (Product product : productPage) {
      responsePage.add(new ProductListResponseDto(product));
    }

    return new ResponseEntity<>(responsePage, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductSpecificResponseDto> listSpecificProduct(
      @PathVariable("id") Long productId) {
    Product product = productService.loadProductForId(productId);

    return new ResponseEntity<>(new ProductSpecificResponseDto(product), HttpStatus.OK);
  }

  @PostMapping("/bid")
  public ResponseEntity<ProductSpecificResponseDto> placeBid(
      @RequestBody PlaceBidRequestDto bidRequest, HttpServletRequest httpRequest) {
    GreenbayUser user =
        (GreenbayUser) userDetailsService.loadUserByUsername(httpRequest.getRemoteUser());
    Product productToBid = productService.loadProductForId(bidRequest.getProductId());
    Bid bid = bidRequest.toBidForUser(productToBid, user);

    ProductSpecificResponseDto productSpecificResponseDto =
        new ProductSpecificResponseDto(productService.placeBid(bid));

    return new ResponseEntity<>(productSpecificResponseDto, HttpStatus.CREATED);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<MessageDto> deleteProduct(@PathVariable("id") Long productId){
    productService.deleteProduct(productId);
    return new ResponseEntity<>(
        new MessageDto(messageService.getMessage("successfully_deleted")), HttpStatus.OK);
  }

  @DeleteMapping("/bid/{id}")
  public ResponseEntity<MessageDto> deleteBid(@PathVariable("id") Long bidId){
    productService.deleteBid(bidId);
    return new ResponseEntity<>(
        new MessageDto(messageService.getMessage("successfully_deleted")), HttpStatus.OK);
  }

}
