package com.gfa.greenbay.dtos;

import com.gfa.greenbay.entities.Product;
import java.util.Objects;

public class ProductListResponseDto {

  private String name;
  private String photoUrl;
  private Integer lastBid;

  public ProductListResponseDto() {
  }

  public ProductListResponseDto(Product product) {
    this.name = product.getName();
    this.photoUrl = product.getPhotoUrl();
    this.lastBid = product.getLastBid();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPhotoUrl() {
    return photoUrl;
  }

  public void setPhotoUrl(String photoUrl) {
    this.photoUrl = photoUrl;
  }

  public Integer getLastBid() {
    return lastBid;
  }

  public void setLastBid(Integer lastBid) {
    this.lastBid = lastBid;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ProductListResponseDto that = (ProductListResponseDto) o;
    return Objects.equals(name, that.name) && Objects.equals(photoUrl,
        that.photoUrl) && Objects.equals(lastBid, that.lastBid);
  }

  @Override
  public int hashCode() {
    return 0;
  }
}
