package com.ezshop.desensitize.util;

import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class ReflectionUtils {
    private static Logger logger = LoggerFactory.getLogger(ReflectionUtils.class);
    private static final Map<Type, List<Field>> fieldOfTypeHolder = new ConcurrentHashMap<>(8);

    /**
     * get all property fields.
     *
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
     *
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
     *
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
            logger.error("IntrospectionException occurred. clazz[{}],field[{}]", clazz.getName(), field.getName());
            return false;
        }
    }

    /**
     * 判断class是否为引用类型，排除
     *
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
     * 根据Method对象获取Override接口的Method对象
     *
     * @param method
     * @return
     */
    public static Method getInterfaceMethod(Class<?> clazz, Method method) {
        if (clazz.isInterface()) {
            return method;
        }

        for (Class<?> interfaceClazz : clazz.getInterfaces()) {
            Method interfaceMethod = org.springframework.util.ReflectionUtils.findMethod(
                    interfaceClazz, method.getName(), method.getParameterTypes());
            if (interfaceMethod != null) {
                return interfaceMethod;
            }
        }

        Class<?> superClazz = clazz.getSuperclass();
        if (superClazz != null && !Object.class.equals(superClazz)) {
            return getInterfaceMethod(superClazz, method);
        } else {
            return null;
        }
    }

    /**
     * 获取类及其父类中带有指定注解的所有方法
     *
     * @param targetType          目标类
     * @param annotationType      目标注解
     * @param interfaceMethodList 带有目标注解的接口方法列表
     * @return
     */
    public static <A extends Annotation> void getInterfaceMethodsWithAnnotation(Class<?> targetType,
                                                                                Class<A> annotationType,
                                                                                List<Method> interfaceMethodList) {
        Method[] methods = targetType.getDeclaredMethods();
        if (methods != null) {
            for (int i = 0; i < methods.length; i++) {
                Annotation annotation = methods[i].getAnnotation(annotationType);
                if (annotation != null) {
                    Method interfaceMethod = ReflectionUtils.getInterfaceMethod(targetType, methods[i]);
                    if (interfaceMethod != null) {
                        interfaceMethodList.add(interfaceMethod);
                    }
                }
            }
        }

        if (targetType.getSuperclass() != null && !Object.class.equals(targetType.getSuperclass())) {
            getInterfaceMethodsWithAnnotation(targetType.getSuperclass(), annotationType, interfaceMethodList);
        }
    }

    /**
     * 根据泛型类实际类型
     *
     * @param targetType
     * @return
     */
    public static Class<?> getActualGenericType(Type targetType) throws Exception {
        if (targetType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) targetType;
            Type[] innerTypes = parameterizedType.getActualTypeArguments();
            if (innerTypes != null && innerTypes.length == 1) {
                return getActualGenericType(innerTypes[0]);
            } else {
                throw new RuntimeException("不支持多个泛型类型参数解析。");
            }
        } else {
            if (targetType instanceof WildcardType) {
                throw new RuntimeException("不支持通配符泛型参数解析。");
            }
            Class<?> targetClazz = (Class<?>) targetType;
            if (Collection.class.isAssignableFrom(targetClazz)) {
                throw new RuntimeException("Collection类型返回值缺少泛型参数。");
            }
            return targetClazz;
        }
    }

//    /**
//     * 获取方法带ApiParam注解的参数列表(JDK1.8及之后)
//     *
//     * @param method
//     * @return
//     * @throws Exception
//     */
//    public static List<String> getMethodParameterNames(Method method) throws Exception {
//        Parameter[] parameters = method.getParameters();
//        if (parameters != null && parameters.length > 0) {
//            List<String> argNames = new ArrayList<>(parameters.length);
//            for (int i = 0; i < parameters.length; i++) {
//                ApiParam apiParams = parameters[i].getAnnotation(ApiParam.class);
//                if (apiParams != null) {
//                    argNames.add(apiParams.name());
//                } else {
//                    String msg = MessageFormat.format("参数缺少ApiParam注解。Class[{0}],Method[{1}],Parameter[{2}]",
//                            method.getDeclaringClass().getName(), method.getName(), parameters[i].getName());
//                    throw new Exception(msg);
//                }
//            }
//            return argNames;
//        }
//        return null;
//    }

    /**
     * 获取方法带ApiParam注解的参数列表 (JDK1.7及以前)
     *
     * @param method
     * @return
     * @throws Exception
     */
    public static List<String> getMethodParameterNames(Method method) throws Exception {
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        if (parameterAnnotations != null && parameterAnnotations.length > 0) {
            List<String> argNames = new ArrayList<>(parameterAnnotations.length);
            String argName = null;
            for (int i = 0; i < parameterAnnotations.length; i++) {
                if (parameterAnnotations[i] != null && parameterAnnotations[i].length > 0) {
                    for (int j = 0; j < parameterAnnotations[i].length; j++) {
                        if (parameterAnnotations[i][j] instanceof ApiParam) {
                            argName = ((ApiParam)parameterAnnotations[i][j]).value();
                            break;
                        }
                    }
                }

                if (argName != null) {
                    argNames.add(argName);
                } else {
                    String msg = MessageFormat.format("参数缺少ApiParam注解。Class[{0}],Method[{1}]",
                            method.getDeclaringClass().getName(), method.getName());
                    throw new Exception(msg);
                }
            }
            return argNames;
        }
        return null;
    }
}
