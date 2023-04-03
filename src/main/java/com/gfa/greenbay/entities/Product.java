package com.gfa.greenbay.entities;

import com.gfa.greenbay.dtos.ProductDto;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "products")
@SQLDelete(sql = "UPDATE products SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String description;
  private String photoUrl;
  private Integer startingPrice;
  private Integer purchasePrice;
  private Boolean deleted = false;
  private Integer lastBid = 0;

  public Product() {
  }

  public Product(
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

  public Product(ProductDto productDto) {
    this.name = productDto.getName();
    this.description = productDto.getDescription();
    this.photoUrl = productDto.getPhotoUrl();
    this.startingPrice = productDto.getStartingPrice();
    this.purchasePrice = productDto.getPurchasePrice();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public Boolean getDeleted() {
    return deleted;
  }

  public void setDeleted() {
    this.deleted = true;
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
    Product product = (Product) o;
    return
        Objects.equals(id, product.id)
            && Objects.equals(name, product.name)
            && Objects.equals(description, product.description)
            && Objects.equals(photoUrl, product.photoUrl)
            && Objects.equals(startingPrice, product.startingPrice)
            && Objects.equals(purchasePrice, product.purchasePrice)
            && Objects.equals(deleted, product.deleted)
            && Objects.equals(lastBid, product.lastBid);
  }

  @Override
  public int hashCode() {
    return 0;
  }
}
