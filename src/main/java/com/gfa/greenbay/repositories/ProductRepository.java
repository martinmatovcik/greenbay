package com.gfa.greenbay.repositories;

import com.gfa.greenbay.entities.Product;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

  Page<Product> findAllBySoldFalse(Pageable pageable);
  Optional<Product> findById(Long id);
}
