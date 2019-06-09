package com.ezddd.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class ClassUtil {
    private static final Logger log = LoggerFactory.getLogger(ClassUtil.class);

    /**
     * 利用递归找一个类的指定方法，如果找不到，去父亲里面找直到最上层Object对象为止。
     *
     * @param clazz      目标类
     * @param methodName 方法名
     * @param classes    方法参数类型数组
     * @return 方法对象
     * @throws Exception
     */
    public static Method getMethod(Class clazz, String methodName,
                                   final Class[] classes) throws Exception {
        Method method = null;
        try {
            method = clazz.getDeclaredMethod(methodName, classes);
        } catch (NoSuchMethodException e) {
            try {
                method = clazz.getMethod(methodName, classes);
            } catch (NoSuchMethodException ex) {
                if (clazz.getSuperclass() == null) {
                    return method;
                } else {
                    method = getMethod(clazz.getSuperclass(), methodName,
                            classes);
                }
            }
        }
        return method;
    }

    /**
     * @param obj        调整方法的对象
     * @param methodName 方法名
     * @param classes    参数类型数组
     * @param objects    参数数组
     * @return 方法的返回值
     */
    public static Object invoke(final Object obj, final String methodName,
                                final Class[] classes, final Object[] objects) {
        try {
            Method method = getMethod(obj.getClass(), methodName, classes);
            method.setAccessible(true);
            return method.invoke(obj, objects);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Class<?> forName(String typeName) {
        try {
            Class<?> type = Class.forName(typeName);
            return type;
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage(), e);
            throw new IllegalArgumentException(
                    "Class of " + typeName + " not found. ", e);
        }
    }

    public static void setField(Object target, String fieldName, Object value) {
        try {
            Class<?> targetType = target.getClass();
            Field field = findFiledIncludeSuperClass(targetType, fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
            throw new IllegalArgumentException(
                    "Failed to access " + fieldName + " field. ", e);
        }
    }

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

    public static Field findFiledIncludeSuperClass(Class<?> targetType, String fieldName) {
        Assert.notNull(targetType, "targetType must not be null.");
        Assert.notNull(fieldName, "fieldName must not be null.");
        try {
            return targetType.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            Class<?> superClazz = targetType.getSuperclass();
            if (Object.class.equals(superClazz)) {
                throw new IllegalArgumentException(
                        "Field of " + fieldName + " not found. ", e);
            } else {
                return findFiledIncludeSuperClass(superClazz, fieldName);
            }
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

    public static <T> T getFieldValue(Object target, Field field) {
        try {
            field.setAccessible(true);
            return (T) field.get(target);
        } catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
            throw new IllegalArgumentException(
                    "Failed to access " + field.getName() + " field. ", e);
        }
    }

    public static <T> T invokeMehtod(Object target, Method method, Object... args) {
        try {
            return (T) method.invoke(target, args);
        } catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
            throw new IllegalArgumentException(
                    "Failed to access " + method.getName() + " field. ", e);
        } catch (InvocationTargetException e) {
            log.error(e.getMessage(), e);
            throw new IllegalArgumentException(
                    "Failed to invoke " + method.getName() + " method. ", e);
        }
    }

    public static <A extends Annotation> A findAnnotation(Class<?> clazz, Class<A> annotationType) {
        Assert.notNull(clazz, "clazz must not be null.");
        return clazz.getAnnotation(annotationType);
    }

}
