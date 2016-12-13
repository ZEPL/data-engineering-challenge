package com.ychan.dto;

import java.text.MessageFormat;
import java.util.UUID;

public interface Dto {
  default String makeId(final String entity) {
    Object[] params = new Object[]{
        entity,
        UUID.randomUUID().toString()};
    return MessageFormat.format("{0}-{1}", params);
    
  }
}
