package com.ezddd.common.utils;

public class EzStringUtils {
    public static String toLowerCaseFirstOne(String str) {
        if (Character.isLowerCase(str.charAt(0))) {
            return str;
        } else {
            return (new StringBuilder()).append(Character.toLowerCase(str.charAt(0)))
                    .append(str.substring(1)).toString();
        }
    }
}

