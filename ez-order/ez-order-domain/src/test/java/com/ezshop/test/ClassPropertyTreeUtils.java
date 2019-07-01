package com.ezshop.test;

import com.alibaba.fastjson.JSON;
import com.ezshop.desensitize.util.ReflectionUtils;
import org.springframework.util.Assert;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClassPropertyTreeUtils {
    private static final Map<Type, ClassPropertyTreeNode> propertyTreeNodeHolder = new ConcurrentHashMap<>();

    /**
     * 解析方法返回值属性树
     * @param targetMethod
     * @return
     */
    public static ClassPropertyTreeNode parseMethodReturnType(Method targetMethod) {
        Assert.notNull(targetMethod, "targetMmethod must not be null.");
        try {
            Class<?> returnClazz = targetMethod.getReturnType();
            Class<?> actualReturnType = null;
            if (ResultDto.class.isAssignableFrom(returnClazz) || ResultVo.class.isAssignableFrom(returnClazz)) {
                ParameterizedType returnType = (ParameterizedType) targetMethod.getGenericReturnType();
                Type[] types = returnType.getActualTypeArguments();
                if (types != null) {
                    if (types[0] instanceof ParameterizedType) {
                        ParameterizedType type = (ParameterizedType) types[0];
                        if (Collection.class.isAssignableFrom((Class<?>) type.getRawType())) {
                            Type[] innerTypes = type.getActualTypeArguments();
                            if (innerTypes != null) {
                                actualReturnType = (Class<?>) innerTypes[0];
                            }
                        }
                    } else {
                        actualReturnType = (Class<?>) types[0];
                    }
                }
            } else {
                actualReturnType = ReflectionUtils.getActualType(returnClazz);
            }
            return ClassPropertyTreeUtils.parse(actualReturnType);
        } catch (Exception ex) {
            throw new MethodReturnTypeParsingException("解析生成方法返回值属性树时出错了，请检查返回值泛型类型。出错方法:" +
                    targetMethod.toString(), ex);
        }
    }

    /**
     * 解析类属性树
     * @param rootNodeType
     * @return
     */
    public static ClassPropertyTreeNode parse(Class<?> rootNodeType) {
        Assert.notNull(rootNodeType, "rootNodeType must not be null.");
        if (propertyTreeNodeHolder.containsKey(rootNodeType)) {
            return propertyTreeNodeHolder.get(rootNodeType);
        } else {
            ClassPropertyTreeNode classPropertyTreeNode = ClassPropertyTreeNode.build(rootNodeType);
            propertyTreeNodeHolder.put(rootNodeType, classPropertyTreeNode);
            return classPropertyTreeNode;
        }
    }

    public static void main(String[] args) {
        try {
            Class<?> clazz = DemoService.class;
            Method getResultDemoDtoMethod = clazz.getMethod("getResultDemoDtoList", String.class, String.class);
            for (int i = 0; i < 1; i++) {
                ClassPropertyTreeNode classPropertyTreeNode =
                        ClassPropertyTreeUtils.parseMethodReturnType(getResultDemoDtoMethod);
                System.out.println(JSON.toJSONString(classPropertyTreeNode));
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
