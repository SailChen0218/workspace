package com.ezddd.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * <p>标题: </p>
 * <p>功能描述: 对象属性复制工具类</p>
 * <p>
 * <p>版权: 税友软件集团股份有限公司</p>
 * <p>创建时间: 2018/10/15</p>
 * <p>作者：cqf</p>
 * <p>修改历史记录：</p>
 * ====================================================================<br>
 */
public class BeanCopyUtils {
    private static final Logger log = LoggerFactory.getLogger(BeanCopyUtils.class);
    private static final Map<String, ClassPropertyDescriptor> classPropertyDescriptorMap = new ConcurrentHashMap<>();

    /**
     * 根据类型复制List
     *
     * @param source
     * @param clz
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S, T> T cloneByProperties(S source, Class<T> clz) {
        if (source == null) {
            return null;
        } else {
            T t = BeanUtils.instantiate(clz);
            copyProperties(source, t);
            return t;
        }
    }

    /**
     * 根据类型复制List
     *
     * @param sourceList
     * @param clz
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S, T> List<T> cloneListByProperties(List<S> sourceList, Class<T> clz) {
        List<T> targetList = new ArrayList<>(sourceList.size());
        for (S source : sourceList) {
            T t = cloneByProperties(source, clz);
            targetList.add(t);
        }
        return targetList;
    }

    /**
     * 属性复制
     *
     * @param source the source bean
     * @param target the target bean
     * @throws BeansException if the copying failed
     * @see BeanWrapper
     */
    private static void copyProperties(Object source, Object target)
            throws BeansException {

        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");

        Class<?> sourceClazz = source.getClass();
        Class<?> targetClazz = target.getClass();

        boolean isTransformBaseCode = false;
        String[] types = null;
        String[] codeFields = null;
        String[] nameFields = null;
        TransformBaseCode baseCodeAnnotation = targetClazz.getAnnotation(TransformBaseCode.class);
        if (baseCodeAnnotation != null) {
            types = baseCodeAnnotation.type();
            codeFields = baseCodeAnnotation.codeField();
            nameFields = baseCodeAnnotation.nameField();
            if (types.length == codeFields.length
                    && types.length == nameFields.length
                    && codeFields.length == nameFields.length) {
                isTransformBaseCode = true;
            }
        }

        boolean isTransformNullToDefault = false;
        Class<?>[] clazzs = null;
        String[] defaultValues = null;
        TransformNullToDefault transformNullToDefault = targetClazz.getAnnotation(TransformNullToDefault.class);
        if (transformNullToDefault != null) {
            clazzs = transformNullToDefault.clazz();
            defaultValues = transformNullToDefault.defaultValue();
            if (clazzs.length == defaultValues.length) {
                isTransformNullToDefault = true;
            }
        }

        ClassPropertyDescriptor targetCpd = getClassPropertyDescriptor(targetClazz);
        ClassPropertyDescriptor sourceCpd = getClassPropertyDescriptor(sourceClazz);

        Map<String, Field> targgetPropertyFieldMap = targetCpd.getPropertyFieldMap();
        for (String propertyName : targgetPropertyFieldMap.keySet()) {
            try {

                Field targetField = targetCpd.getPropertyFiled(propertyName);
                Field sourceFiled = sourceCpd.getPropertyFiled(propertyName);
                Object value = null;
                if (sourceFiled != null) {
                    value = ClassUtil.getFieldValue(source, sourceFiled);
                }

//            if (isTransformBaseCode) {
//                value = transformBaseCode(source, sourceClazz, value, targetField, types, codeFields, nameFields);
//            }

                ConvertDate convertDate = targetField.getAnnotation(ConvertDate.class);
                if (convertDate != null) {
                    Field sourceField = sourceCpd.getPropertyFiled(propertyName);
                    try {
                        value = convertDate(sourceField, value, convertDate);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                if (isTransformNullToDefault) {
                    value = transformNullToDefault(targetField, value, clazzs, defaultValues);
                }

                targetField.setAccessible(true);
                targetField.set(target, value);
            } catch (IllegalAccessException ex) {
                throw new FatalBeanException(
                        "Could not copy property '" + propertyName + "' from source to target", ex);
            }
        }


//        for (PropertyDescriptor targetPd: targetPds) {
//            Method writeMethod = targetPd.getWriteMethod();
//            if (writeMethod != null) {
//                try {
//
//                    Field targetField = ClassUtil.findFiledIncludeSuperClass(targetClazz, targetPd.getName());
//                    PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(sourceClazz, targetPd.getName());
//                    Object value = null;
//                    if (sourcePd != null) {
//                        Method readMethod = sourcePd.getReadMethod();
//                        if (readMethod != null) {
//                            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
//                                readMethod.setAccessible(true);
//                            }
//                            value = readMethod.invoke(source);
//                        }
//                    }
//
//                    if (isTransformBaseCode) {
//                        value = transformBaseCode(source, sourceClazz, value, targetField, types, codeFields, nameFields);
//                    }
//
//                    ConvertDate convertDate = targetField.getAnnotation(ConvertDate.class);
//                    if (convertDate != null) {
//                        Field sourceField = sourceClazz.getDeclaredField(sourcePd.getName());
//                        value = transformDate(sourceField, value, convertDate);
//                    }
//
//                    if (isTransformNullToDefault) {
//                        value = transformNullToDefault(targetField, value, clazzs, defaultValues);
//                    }
//
//                    if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
//                        writeMethod.setAccessible(true);
//                    }
//                    writeMethod.invoke(target, value);
//
//                } catch (Throwable ex) {
//                    throw new FatalBeanException(
//                            "Could not copy property '" + targetPd.getName() + "' from source to target", ex);
//                }
//            }
//        }
    }

    /**
     * 日期格式转换
     *
     * @param sourceField
     * @param value
     * @param dateFormatAnnotation
     * @throws Exception
     */
    private static Object convertDate(Field sourceField, Object value, ConvertDate dateFormatAnnotation) throws Exception {
        try {
            if (sourceField == null) {
                return value;
            }

            if (value == null) {
                return value;
            }

            // 根据注释格式转换日期字段格式
            SimpleDateFormat targetFormatter = new SimpleDateFormat(dateFormatAnnotation.targetFormat());
            Class<?> sourceFieldType = sourceField.getType();
            if (Date.class.equals(sourceFieldType)) {
                return targetFormatter.format(value);
            } else if (String.class.equals(sourceFieldType)) {
                SimpleDateFormat sourceFormatter = new SimpleDateFormat(dateFormatAnnotation.sourceFormat());
                Date date = sourceFormatter.parse(String.valueOf(value));
                return targetFormatter.format(date);
            } else if (long.class.equals(sourceFieldType)) {
                Date date = new Date((long) value);
                return targetFormatter.format(date);
            } else {
                log.error("仅支持Date、String、long类型字段的日期格式转换。sourceFiledType:" + sourceField.getType());
                return value;
            }

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    /**
     * 转换basecode
     *
     * @param source
     * @param sourceClazz
     * @param value
     * @param targetField
     * @param types
     * @param codeFields
     * @param nameFields
     * @return
     * @throws Exception
     */
    private static Object transformBaseCode(Object source,
                                            Class<?> sourceClazz,
                                            Object value,
                                            Field targetField,
                                            String[] types,
                                            String[] codeFields,
                                            String[] nameFields) throws Exception {
        if (!StringUtils.isEmpty(value)) {
            for (int i = 0; i < nameFields.length; i++) {
                if (targetField.getName().equals(nameFields[i])) {
                    PropertyDescriptor codePd = BeanUtils.getPropertyDescriptor(sourceClazz, codeFields[i]);
                    if (codePd != null) {
                        String codeFieldValue = String.valueOf(codePd.getReadMethod().invoke(source));
                        //TODO: basecode need
//                        String nameFieldValue = BaseCodeManager.getNameByCode(types[i], codeFieldValue);
                        String nameFieldValue = "";
                        return nameFieldValue;
                    }
                }
            }
        }
        return value;
    }

    /**
     * 转换null为默认值
     *
     * @param targetField
     * @param value
     * @param clazzs
     * @param defaultValues
     * @return
     */
    private static Object transformNullToDefault(Field targetField,
                                                 Object value,
                                                 Class<?>[] clazzs,
                                                 String[] defaultValues) {
        if (value == null) {
            for (int i = 0; i < clazzs.length; i++) {
                if (targetField.getType().equals(clazzs[i])) {
                    return defaultValues[i];
                }
            }
        }
        return value;
    }

    private static ClassPropertyDescriptor getClassPropertyDescriptor(Class<?> clazz) {
        String className = clazz.getName();
        if (classPropertyDescriptorMap.containsKey(className)) {
            return classPropertyDescriptorMap.get(className);
        } else {
            ClassPropertyDescriptor classPropertyDescriptor = ClassPropertyDescriptor.build(clazz);
            classPropertyDescriptorMap.put(className, classPropertyDescriptor);
            return classPropertyDescriptor;
        }
    }

    private static class ClassPropertyDescriptor {
        private String className;
        private Map<String, Field> propertyFieldMap;

        private static ClassPropertyDescriptor build(Class<?> clazz) {
            ClassPropertyDescriptor classPropertyDescriptor = new ClassPropertyDescriptor();
            PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(clazz);
            List<Field> fieldList = new ArrayList<>();
            ClassUtil.findFiledsIncludeSuperClass(clazz, fieldList);
            Map<String, Field> fieldMap = fieldList.stream().collect(Collectors.toMap(Field::getName, a -> a, (k1, k2) -> k1));
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                Field field = fieldMap.get(propertyDescriptor.getName());
                classPropertyDescriptor.addPropertyField(propertyDescriptor.getName(), field);
            }
            classPropertyDescriptor.className = clazz.getName();
            return classPropertyDescriptor;
        }

        public void addPropertyField(String propertyName, Field field) {
            propertyFieldMap.put(propertyName, field);
        }

        public Field getPropertyFiled(String propertyName) {
            if (propertyFieldMap.containsKey(propertyName)) {
                return propertyFieldMap.get(propertyName);
            }
            return null;
        }

        public Map<String, Field> getPropertyFieldMap() {
            return this.propertyFieldMap;
        }
    }


}



