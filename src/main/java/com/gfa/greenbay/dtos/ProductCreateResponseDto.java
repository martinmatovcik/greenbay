package com.gfa.greenbay.dtos;

import com.gfa.greenbay.entities.Product;
import java.util.Objects;

public class ProductCreateResponseDto extends ProductDto {

  private Long id;

  public ProductCreateResponseDto() {
  }

  public ProductCreateResponseDto(
      Long id,
      String name,
      String description,
      String photoUrl,
      Integer startingPrice,
      Integer purchasePrice) {
    super(name, description, photoUrl, startingPrice, purchasePrice);
    this.id = id;
  }

  public ProductCreateResponseDto(Product product) {
    super(
        product.getName(),
        product.getDescription(),
        product.getPhotoUrl(),
        product.getStartingPrice(),
        product.getPurchasePrice());
    this.id = product.getId();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    ProductCreateResponseDto that = (ProductCreateResponseDto) o;
    return Objects.equals(id, that.id);
  }
}
