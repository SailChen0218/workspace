package com.ezshop.desensitize.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public abstract class ReflectionUtils {
    private static Logger logger = LoggerFactory.getLogger(ReflectionUtils.class);
    private static final Map<Type, List<Field>> fieldOfTypeHolder = new ConcurrentHashMap<>(8);

    /**
     * get all property fields.
     * @param clazz
     * @return
     */
    public static List<Field> getPropertyFieldsFrom(Class<?> clazz) {
        if (fieldOfTypeHolder.containsKey(clazz)) {
            return fieldOfTypeHolder.get(clazz);
        } else {
            List<Field> fields = new ArrayList<>(8);
            getPropertyFieldsFrom(clazz, fields);
            return fields;
        }
    }

    /**
     * 递归循环遍历class文件获取所有字段
     * @param clazz
     * @param fieldList
     */
    private static void getPropertyFieldsFrom(Class<?> clazz, List<Field> fieldList) {
        Assert.notNull(clazz, "clazz must not be null.");
        Assert.notNull(fieldList, "fieldList must not be null.");
        Field[] fields = clazz.getDeclaredFields();
        if (fields != null && fields.length > 0) {
            for (int i = 0; i < fields.length; i++) {
                if (isPropertyField(clazz, fields[i])) {
                    fieldList.add(fields[i]);
                }
            }
        }
        Class<?> superClazz = clazz.getSuperclass();
        if (!Object.class.equals(superClazz)) {
            getPropertyFieldsFrom(superClazz, fieldList);
        }
    }

    /**
     * 判断字段是否为属性
     * @param clazz
     * @param field
     * @return
     */
    private static boolean isPropertyField(Class<?> clazz, Field field) {
        try {
            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
            Method getMethod = pd.getReadMethod();
            if (getMethod == null) {
                return false;
            }
            return true;
        } catch (IntrospectionException e) {
            logger.error("IntrospectionException occurred. clazz[{}],field[{}]", clazz.getName(), field.getName(), e);
            return false;
        }
    }

    /**
     * 判断class是否为引用类型，排除
     * @param clazz
     * @return
     */
    public static boolean isReferenceType(Class<?> clazz) {
        if (clazz == null) {
            return false;
        }
        if (clazz.isPrimitive()) {
            return false;
        }
        if (Number.class.isAssignableFrom(clazz)) {
            return false;
        }
        if (String.class.equals(clazz)) {
            return false;
        }
        if (Object.class.equals(clazz)) {
            return false;
        }
        return true;
    }


    /**
     * 根据class获取实际元素类型
     * @param targetType
     * @return
     */
    public static Class<?> getActualType(Class<?> targetType) {
        if (Collection.class.isAssignableFrom(targetType)) {
            return targetType.getTypeParameters()[0].getClass();
        }
        return targetType;
    }
}
