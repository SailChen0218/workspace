package com.ezddd.core.utils;

import com.ezddd.core.annotation.EzIdentifier;
import com.ezddd.core.annotation.EzVersion;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AggregateUtil {
    private static Map<String, AggregateDefinition> aggregateDefinitionHolder = new ConcurrentHashMap<>();

    public static String generateID() {
        return UUID.randomUUID().toString();
    }

    public static String getIdentifierFrom(Object aggregate) {
        Class<?> aggregateType = aggregate.getClass();
        AggregateDefinition aggregateDefinition = AggregateUtil.findAggregateDefinition(aggregateType);
        return ClassUtil.invokeMehtod(aggregate, aggregateDefinition.identifierReadMehtod);
    }

    public static long getVersionFrom(Object aggregate) {
        Class<?> aggregateType = aggregate.getClass();
        AggregateDefinition aggregateDefinition = AggregateUtil.findAggregateDefinition(aggregateType);
        return ClassUtil.invokeMehtod(aggregate, aggregateDefinition.versionReadMethod);
    }

    private static <T> AggregateDefinition<T> findAggregateDefinition(Class<T> aggregateType) {
        String aggregateTypeName = aggregateType.getName();
        AggregateDefinition aggregateDefinition = null;
        if (!aggregateDefinitionHolder.containsKey(aggregateTypeName)) {
            aggregateDefinition = AggregateUtil.buildAggregateDefinition(aggregateType);
            aggregateDefinitionHolder.put(aggregateTypeName, aggregateDefinition);
        } else {
            aggregateDefinition = aggregateDefinitionHolder.get(aggregateTypeName);
        }
        return aggregateDefinition;
    }

    private static <T> AggregateDefinition<T> buildAggregateDefinition(Class<T> aggregateType) {
        AggregateDefinition aggregateDefinition = new AggregateDefinition();
        Field identifierFiled = null;
        Field versionFiled = null;
        List<Field> fieldList = new ArrayList<>();
        ClassUtil.findFiledsIncludeSuperClass(aggregateType, fieldList);
        if (fieldList.size() == 0) {
            throw new IllegalArgumentException("Identifier field no found in " + aggregateType.getName());
        } else {
            for (int i = 0; i < fieldList.size(); i++) {
                EzIdentifier ezIdentifier = fieldList.get(i).getAnnotation(EzIdentifier.class);
                if (ezIdentifier != null) {
                    if (identifierFiled != null) {
                        throw new IllegalArgumentException("There must be one and only one identifier field of "
                                + aggregateType.getName());
                    } else {
                        identifierFiled = fieldList.get(i);
                    }
                }

                EzVersion ezVersion = fieldList.get(i).getAnnotation(EzVersion.class);
                if (ezVersion != null) {
                    if (versionFiled != null) {
                        throw new IllegalArgumentException("There must be one and only one version field of "
                                + aggregateType.getName());
                    } else {
                        versionFiled = fieldList.get(i);
                    }
                }
            }
        }

        PropertyDescriptor propertyDescriptor =
                BeanUtils.getPropertyDescriptor(aggregateType, identifierFiled.getName());
        Method identifierReadMehtod = propertyDescriptor.getReadMethod();
        aggregateDefinition.setIdentifierFiled(identifierFiled);
        aggregateDefinition.setIdentifierReadMehtod(identifierReadMehtod);

        propertyDescriptor =
                BeanUtils.getPropertyDescriptor(aggregateType, versionFiled.getName());
        Method versionReadMethod = propertyDescriptor.getReadMethod();
        aggregateDefinition.setVersionFiled(versionFiled);
        aggregateDefinition.setVersionReadMethod(versionReadMethod);
        return aggregateDefinition;
    }

    private static Field getIdentifierField(Class<?> aggregateType) {
        Field identifierFiled = null;
        List<Field> fieldList = new ArrayList<>();
        ClassUtil.findFiledsIncludeSuperClass(aggregateType, fieldList);
        if (fieldList.size() == 0) {
            throw new IllegalArgumentException("aggregateType should have identifier field.");
        } else {
            for (int i = 0; i < fieldList.size(); i++) {
                EzIdentifier ezIdentifier = fieldList.get(i).getAnnotation(EzIdentifier.class);
                if (ezIdentifier != null) {
                    if (identifierFiled != null) {
                        throw new IllegalArgumentException("concreteComponent should have only one identifier field.");
                    } else {
                        identifierFiled = fieldList.get(i);
                    }
                }
            }
        }
        return identifierFiled;
    }

    private static class AggregateDefinition<T> {
        private Class<T> aggregateType;
        private Field identifierFiled;
        private Field versionFiled;
        private Method identifierReadMehtod;
        private Method versionReadMethod;

        public Class<T> getAggregateType() {
            return aggregateType;
        }

        public void setAggregateType(Class<T> aggregateType) {
            this.aggregateType = aggregateType;
        }

        public Field getIdentifierFiled() {
            return identifierFiled;
        }

        public void setIdentifierFiled(Field identifierFiled) {
            this.identifierFiled = identifierFiled;
        }

        public Field getVersionFiled() {
            return versionFiled;
        }

        public void setVersionFiled(Field versionFiled) {
            this.versionFiled = versionFiled;
        }

        public Method getIdentifierReadMehtod() {
            return identifierReadMehtod;
        }

        public void setIdentifierReadMehtod(Method identifierReadMehtod) {
            this.identifierReadMehtod = identifierReadMehtod;
        }

        public Method getVersionReadMethod() {
            return versionReadMethod;
        }

        public void setVersionReadMethod(Method versionReadMethod) {
            this.versionReadMethod = versionReadMethod;
        }
    }
}
