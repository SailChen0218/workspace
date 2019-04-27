package com.ezddd.common.utils;

import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.List;

public class EzBeanUtils {
    public static void findFiledsIncludeSuperClass(Class<?> clazz, List<Field> fieldList) {
        Assert.notNull(clazz, "clazz must not be null.");
        Assert.notNull(fieldList, "fieldList must not be null.");
        Field[] fields = clazz.getDeclaredFields();
        if (fields != null && fields.length > 0) {
            for (int i = 0; i < fields.length; i++) {
                fieldList.add(fields[i]);
            }
        }
        Class<?> superClazz = clazz.getSuperclass();
        if (!Object.class.equals(superClazz)) {
            findFiledsIncludeSuperClass(superClazz, fieldList);
        }
    }
}
