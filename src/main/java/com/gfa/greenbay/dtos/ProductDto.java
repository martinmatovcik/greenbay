package com.gfa.greenbay.dtos;

import com.gfa.greenbay.entities.GreenbayUser;
import com.gfa.greenbay.entities.Product;
import java.util.Objects;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ProductDto {
  @NotBlank(message = "Name can not be empty.")
  private String name;

  @NotBlank(message = "Description can not be empty.")
  private String description;

  @NotBlank(message = "Photo-url can not be empty.")
  @org.hibernate.validator.constraints.URL(message = "Please provide valid URL")
  private String photoUrl;

  @NotNull(message = "Starting price can not be empty.")
  @Min(value = 0, message = "Starting price needs to be positive integer.")
  private Integer startingPrice;

  @NotNull(message = "Purchase price can not be empty.")
  @Min(value = 0, message = "Starting price needs to be positive integer.")
  private Integer purchasePrice;

  public ProductDto() {}

  public ProductDto(
      String name,
      String description,
      String photoUrl,
      Integer startingPrice,
      Integer purchasePrice) {
    this.name = name;
    this.description = description;
    this.photoUrl = photoUrl;
    this.startingPrice = startingPrice;
    this.purchasePrice = purchasePrice;
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

  public Integer getStartingPrice() {
    return startingPrice;
  }

  public void setStartingPrice(Integer startingPrice) {
    this.startingPrice = startingPrice;
  }

  public Integer getPurchasePrice() {
    return purchasePrice;
  }

  public void setPurchasePrice(Integer purchasePrice) {
    this.purchasePrice = purchasePrice;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ProductDto that = (ProductDto) o;
    return Objects.equals(name, that.name)
        && Objects.equals(description, that.description)
        && Objects.equals(photoUrl, that.photoUrl)
        && Objects.equals(startingPrice, that.startingPrice)
        && Objects.equals(purchasePrice, that.purchasePrice);
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public Product toProductForUser(GreenbayUser user) {
    Product product = new Product();

    product.setName(this.name);
    product.setDescription(this.description);
    product.setPhotoUrl(this.photoUrl);
    product.setStartingPrice(this.startingPrice);
    product.setPurchasePrice(this.purchasePrice);
    product.setSeller(user);

    return product;
  }
}
