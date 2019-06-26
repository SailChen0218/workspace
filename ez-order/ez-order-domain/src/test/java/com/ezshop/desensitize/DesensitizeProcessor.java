package com.ezshop.desensitize;

public interface DesensitizeProcessor {
    void desensitize(Object target, String qdbh, String jyfwbh) throws DesensitizeFailedException;
}
