package com.gfa.greenbay.repositories;

import com.gfa.greenbay.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {}
