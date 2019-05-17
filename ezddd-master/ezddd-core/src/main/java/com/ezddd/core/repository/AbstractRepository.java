package com.ezddd.core.repository;

import com.ezddd.core.annotation.EzIdentifier;
import com.ezddd.core.utils.ClassUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRepository<T> implements Repository<T> {
    protected Class<T> aggregateType;
    private Field identifierFiled;
    private Method identifierReadMehtod;
    public AbstractRepository(Class<T> aggregateType) {
        Assert.notNull("aggregateType must not be null. ");
        this.aggregateType = aggregateType;
        resolveIdentifierField();

        PropertyDescriptor propertyDescriptor =
                BeanUtils.getPropertyDescriptor(aggregateType, identifierFiled.getName());
        identifierReadMehtod = propertyDescriptor.getReadMethod();
    }

    public Class<T> getAggregateType() {
        return aggregateType;
    }

    protected String getIdentifierFrom(T aggregate) {
        return ClassUtil.invokeMehtod(aggregate, identifierReadMehtod);
    }

    private void resolveIdentifierField() {
        List<Field> fieldList = new ArrayList<>();
        ClassUtil.findFiledsIncludeSuperClass(aggregateType, fieldList);
        if (fieldList.size() == 0) {
            throw new IllegalArgumentException("aggregateType should have identifier field.");
        } else {
            for (int i = 0; i < fieldList.size(); i++) {
                EzIdentifier ezIdentifier = fieldList.get(i).getAnnotation(EzIdentifier.class);
                if (ezIdentifier != null) {
                    if (this.identifierFiled != null) {
                        throw new IllegalArgumentException("concreteComponent should have only one identifier field.");
                    } else {
                        this.identifierFiled = fieldList.get(i);
                    }
                }
            }
        }
    }
}
