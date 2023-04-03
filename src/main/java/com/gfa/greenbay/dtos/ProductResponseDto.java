package com.gfa.greenbay.dtos;

import com.gfa.greenbay.entities.Product;
import java.util.Objects;

public class ProductResponseDto extends ProductDto {
  private Long id;

  public ProductResponseDto() {}

  public ProductResponseDto(
      Long id,
      String name,
      String description,
      String photoUrl,
      Integer startingPrice,
      Integer purchasePrice) {
    super(name, description, photoUrl, startingPrice, purchasePrice);
    this.id = id;
  }

  public ProductResponseDto(Product product) {
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
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    ProductResponseDto that = (ProductResponseDto) o;
    return Objects.equals(id, that.id);
  }
}
