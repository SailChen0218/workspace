package com.ezshop.desensitize.type;

public interface SentitiveTypeFactory {
    SensitiveType getSensitiveType(Class<? extends SensitiveType> clazz);
}
