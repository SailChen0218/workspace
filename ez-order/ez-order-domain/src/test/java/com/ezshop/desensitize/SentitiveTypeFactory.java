package com.ezshop.desensitize;

import com.ezshop.desensitize.exception.SensitizeTypeNotExistsException;

public interface SentitiveTypeFactory {
    SensitiveType getSensitiveType(Class<? extends SensitiveType> clazz) throws SensitizeTypeNotExistsException;
}
