package com.ezshop.desensitize;

public interface SensitiveType<T> {
    String desensitized(T targetValue);
}
