package com.gfa.greenbay.services;

import com.gfa.greenbay.repositories.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserEntityServiceImpl implements UserEntityService {

  private final UserEntityRepository userEntityRepository;

  @Autowired
  public UserEntityServiceImpl(UserEntityRepository userEntityRepository) {
    this.userEntityRepository = userEntityRepository;
  }
}
