package com.gfa.greenbay.configurations;

import com.gfa.greenbay.repositories.GreenbayUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class GreenbayConfiguration {

  private final GreenbayUserRepository userRepository;

  @Autowired
  public GreenbayConfiguration(GreenbayUserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return username ->
        userRepository
            .findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found."));
  }

}
