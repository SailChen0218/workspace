package com.ezddd.core.utils;

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

    public static void findInterfacesIncludeSuperClass(Class<?> clazz, List<Class<?>> interfaceList) {
        Assert.notNull(clazz, "clazz must not be null.");
        Assert.notNull(interfaceList, "interfaceList must not be null.");
        Class<?>[] interfaces = clazz.getInterfaces();
        if (interfaces != null && interfaces.length > 0) {
            for (int i = 0; i < interfaces.length; i++) {
                interfaceList.add(interfaces[i]);
            }
        }
        Class<?> superClazz = clazz.getSuperclass();
        if (!Object.class.equals(superClazz)) {
            findInterfacesIncludeSuperClass(superClazz, interfaceList);
        }
    }
}
