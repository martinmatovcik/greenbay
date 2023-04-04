package com.gfa.greenbay.repositories;

import com.gfa.greenbay.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

  Page<Product> findAllBySold(Boolean deleted, Pageable pageable);
}
