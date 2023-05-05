package com.gfa.greenbay.services;

public interface MessageService {
  String getMessage(String key);

  Integer convertPrice(Integer usdPrice);
}
