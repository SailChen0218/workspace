package com.ezddd.core.utils;

public class StringUtil {
    public static String toLowerCaseFirstOne(String str) {
        if (Character.isLowerCase(str.charAt(0))) {
            return str;
        } else {
            return (new StringBuilder()).append(Character.toLowerCase(str.charAt(0)))
                    .append(str.substring(1)).toString();
        }
    }

    public static String concat(String str1, String str2) {
        return str1 + "_$@_" + str2;
    }
}

