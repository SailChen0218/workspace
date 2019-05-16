package com.ezddd.core.repository;

import com.esotericsoftware.reflectasm.FieldAccess;
import com.ezddd.core.annotation.EzIdentifier;
import org.springframework.util.Assert;

import java.lang.reflect.Field;

public abstract class AbstractRepository<T> implements Repository<T> {
    protected Class<T> aggregateType;
    private FieldAccess fieldAccess;
    private String identifierFiled;

    public AbstractRepository(Class<T> aggregateType) {
        Assert.notNull("aggregateType must not be null. ");
        this.aggregateType = aggregateType;
        resolveIdentifier();
    }

    public Class<T> getAggregateType() {
        return aggregateType;
    }

    protected String getIdentifierFrom(T aggregate) {
        if (fieldAccess == null) {
            this.fieldAccess = FieldAccess.get(this.aggregateType);
        }

        return (String)this.fieldAccess.get(aggregate, identifierFiled);
    }

    private void resolveIdentifier() {
        Field[] fields = aggregateType.getDeclaredFields();
        if (fields == null || fields.length == 0) {
            throw new IllegalArgumentException("aggregateType should have identifier field.");
        } else {
            Field identifierField = null;
            for (int i = 0; i < fields.length; i++) {
                EzIdentifier ezIdentifier = fields[i].getAnnotation(EzIdentifier.class);
                if (ezIdentifier != null) {
                    if (identifierField != null) {
                        throw new IllegalArgumentException("concreteComponent should have only one identifier field.");
                    } else {
                        identifierField = fields[i];
                    }
                }
            }
            this.identifierFiled = identifierField.getName();
        }
    }
}
