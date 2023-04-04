package com.gfa.greenbay.dtos;

import com.gfa.greenbay.entities.Bid;
import java.time.OffsetDateTime;
import java.util.Objects;

public class BidDto {
    private OffsetDateTime createdAt;
    private Integer value;
    private String bidderUsername;

    public BidDto() {
    }

    public BidDto(Bid bid) {
        this.createdAt = bid.getCreatedAt();
        this.value = bid.getValue();
        this.bidderUsername = bid.getUser().getUsername();
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getBidderUsername() {
        return bidderUsername;
    }

    public void setBidderUsername(String bidderUsername) {
        this.bidderUsername = bidderUsername;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BidDto bidDto = (BidDto) o;
        return Objects.equals(value, bidDto.value) && Objects.equals(createdAt, bidDto.createdAt) && Objects.equals(
            bidderUsername, bidDto.bidderUsername);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
