package com.ezddd.core.utils;

public class IdentifierUtil {
    public static String generateID() {
        return UUID.randomUUID().toString();
    }
}
