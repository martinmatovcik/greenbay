package com.gfa.greenbay.services;

import com.gfa.greenbay.entities.GreenbayUser;

public interface GreenbayUserService {
  String register(GreenbayUser userToRegister);

  String login(String username, String password);
}
