package com.ezshop.desensitize;

public interface SentitiveTypeFactory {
    SensitiveType getSensitiveType(Class<? extends SensitiveType> clazz);
}
