package com.gfa.greenbay.controllers;

import com.gfa.greenbay.services.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserEntityController {

  private final UserEntityService userEntityService;

  @Autowired
  public UserEntityController(UserEntityService userEntityService) {
    this.userEntityService = userEntityService;
  }
}
