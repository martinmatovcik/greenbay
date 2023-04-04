package com.gfa.greenbay.entities;

import com.gfa.greenbay.dtos.ProductDto;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "products")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String description;
  private String photoUrl;
  private Integer startingPrice;
  private Integer purchasePrice;
  private Boolean sold = false;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "seller_id")
  private GreenbayUser seller;

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
  @Nullable
  private List<Bid> bids;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "buyer_id")
  @Nullable
  private GreenbayUser buyer;

  public Product() {}

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

  public Product(ProductDto productDto, GreenbayUser seller) {
    this.name = productDto.getName();
    this.description = productDto.getDescription();
    this.photoUrl = productDto.getPhotoUrl();
    this.startingPrice = productDto.getStartingPrice();
    this.purchasePrice = productDto.getPurchasePrice();
    this.seller = seller;
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

  public Boolean isSold() {
    return sold;
  }

  public void setSold() {
    this.sold = true;
  }

  public GreenbayUser getSeller() {
    return seller;
  }

  public void setSeller(GreenbayUser seller) {
    this.seller = seller;
  }

  public List<Bid> getBids() {
    return bids;
  }

  public void setBids(List<Bid> bids) {
    this.bids = bids;
  }

  @Nullable
  public GreenbayUser getBuyer() {
    return buyer;
  }

  public void setBuyer(@Nullable GreenbayUser buyer) {
    this.buyer = buyer;
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
        && Objects.equals(sold, product.sold)
        && Objects.equals(seller, product.seller)
        && Objects.equals(bids, product.bids)
        && Objects.equals(buyer, product.buyer);
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public void placeBid(Bid bid) {
    if (bids != null){
      bids.add(bid);
    }
  }
}
