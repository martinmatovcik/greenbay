package com.gfa.greenbay.entities;

import java.net.URL;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "products")
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String description;
  private URL photoUrl;  //FIXME will it work with DB?
  private Integer startingPrice;
  private Integer purchasePrice;
  private Integer latestBid;

  public Product() {}

  public Product(
      Long id,
      String name,
      String description,
      URL photoUrl,
      Integer startingPrice,
      Integer purchasePrice,
      Integer latestBid) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.photoUrl = photoUrl;
    this.startingPrice = startingPrice;
    this.purchasePrice = purchasePrice;
    this.latestBid = latestBid;
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

  public URL getPhotoUrl() {
    return photoUrl;
  }

  public void setPhotoUrl(URL photoUrl) {
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

  public Integer getLatestBid() {
    return latestBid;
  }

  public void setLatestBid(Integer latestBid) {
    this.latestBid = latestBid;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Product product = (Product) o;
    return Objects.equals(id, product.id)
        && Objects.equals(name, product.name)
        && Objects.equals(description, product.description)
        && Objects.equals(photoUrl, product.photoUrl)
        && Objects.equals(startingPrice, product.startingPrice)
        && Objects.equals(purchasePrice, product.purchasePrice)
        && Objects.equals(latestBid, product.latestBid);
  }

  @Override
  public int hashCode() {
    return 0;
  }
}
