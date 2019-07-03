package com.ezshop.desensitize;

import com.ezshop.desensitize.exception.DesensitizeFailedException;

public interface DesensitizeProcessor {
    void desensitize(Object target, String channel, String service) throws DesensitizeFailedException;
}
