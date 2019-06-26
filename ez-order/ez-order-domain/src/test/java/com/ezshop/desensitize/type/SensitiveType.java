package com.ezshop.desensitize.type;

public interface SensitiveType<T> {
    String desensitized(T targetValue);
}
