package com.gfa.greenbay.services;

import com.gfa.greenbay.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {
  private final MessageSource messageSource;

  @Autowired
  public MessageServiceImpl(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  @Override
  public String getMessage(String key) {
    try {
      return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
    } catch (NoSuchMessageException e) {
      throw new NotFoundException(e.getMessage());
    }
  }

  //TODO - Price converter
  @Override
  public Integer convertPrice(Integer usdPrice) {
    return null;
  }
}
