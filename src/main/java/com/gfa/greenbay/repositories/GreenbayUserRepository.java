package com.gfa.greenbay.repositories;

import com.gfa.greenbay.entities.GreenbayUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GreenbayUserRepository extends JpaRepository<GreenbayUser, Long> {
  Optional<GreenbayUser> findByUsername(String username);
  void deleteById(Long id);
}
