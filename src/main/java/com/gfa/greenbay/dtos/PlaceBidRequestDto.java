package com.gfa.greenbay.dtos;

import com.gfa.greenbay.entities.Bid;
import com.gfa.greenbay.entities.GreenbayUser;
import com.gfa.greenbay.entities.Product;
import java.util.Objects;

public class PlaceBidRequestDto {
  private Integer value;
  private Long productId;

  public PlaceBidRequestDto() {}

  public PlaceBidRequestDto(Integer value, Long productId) {
    this.value = value;
    this.productId = productId;
  }

  public Integer getValue() {
    return value;
  }

  public void setValue(Integer value) {
    this.value = value;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PlaceBidRequestDto that = (PlaceBidRequestDto) o;
    return Objects.equals(value, that.value) && Objects.equals(productId, that.productId);
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public Bid toBidForUser(Product product, GreenbayUser user) {
    return new Bid(product, user, this.value);
  }
}
