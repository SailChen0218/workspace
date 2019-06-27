package com.ezshop.desensitize;

public interface DesensitizeProcessor {
    void desensitize(Object target, String channel, String service) throws DesensitizeFailedException;
}
