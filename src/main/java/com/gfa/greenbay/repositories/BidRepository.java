package com.gfa.greenbay.repositories;

import com.gfa.greenbay.entities.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidRepository extends JpaRepository<Bid, Long> {
  void deleteById(Long id);
}
