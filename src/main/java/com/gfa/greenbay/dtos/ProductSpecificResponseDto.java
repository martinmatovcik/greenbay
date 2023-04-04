package com.gfa.greenbay.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.gfa.greenbay.entities.Bid;
import com.gfa.greenbay.entities.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;

public class ProductSpecificResponseDto {
  private String name;
  private String description;
  private String photoUrl;
  private List<BidDto> bids;
  private Integer purchasePrice;
  private String sellerUsername;
  @JsonInclude(Include.NON_NULL)
  @Nullable
  private String buyersUsername;

  public ProductSpecificResponseDto() {}

  public ProductSpecificResponseDto(Product product) {
    this.name = product.getName();
    this.description = product.getDescription();
    this.description = product.getDescription();
    this.photoUrl = product.getPhotoUrl();
    this.bids = castBidsListToBidsDtoList(product.getBids());
    this.purchasePrice = product.getPurchasePrice();
    this.sellerUsername = product.getSeller().getUsername();
    if (product.isSold()) {
      this.buyersUsername = product.getBuyer().getUsername();
    }
  }

  private List<BidDto> castBidsListToBidsDtoList(List<Bid> bids) {
    List<BidDto> bidDtos = new ArrayList<>();
    for (Bid bid : bids) {
      bidDtos.add(new BidDto(bid));
    }
    return bidDtos;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getPhotoUrl() {
    return photoUrl;
  }

  public void setPhotoUrl(String photoUrl) {
    this.photoUrl = photoUrl;
  }

  public List<BidDto> getBids() {
    return bids;
  }

  public void setBids(List<BidDto> bids) {
    this.bids = bids;
  }

  public Integer getPurchasePrice() {
    return purchasePrice;
  }

  public void setPurchasePrice(Integer purchasePrice) {
    this.purchasePrice = purchasePrice;
  }

  public String getSellerUsername() {
    return sellerUsername;
  }

  public void setSellerUsername(String sellerUsername) {
    this.sellerUsername = sellerUsername;
  }

  @Nullable
  public String getBuyersUsername() {
    return buyersUsername;
  }

  public void setBuyersUsername(@Nullable String buyersUsername) {
    this.buyersUsername = buyersUsername;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    ProductSpecificResponseDto that = (ProductSpecificResponseDto) o;
    return Objects.equals(name, that.name) && Objects.equals(description, that.description)
        && Objects.equals(photoUrl, that.photoUrl) && Objects.equals(bids, that.bids)
        && Objects.equals(purchasePrice, that.purchasePrice) && Objects.equals(sellerUsername,
        that.sellerUsername) && Objects.equals(buyersUsername, that.buyersUsername);
  }

  @Override
  public int hashCode() {
    return 0;
  }
}
