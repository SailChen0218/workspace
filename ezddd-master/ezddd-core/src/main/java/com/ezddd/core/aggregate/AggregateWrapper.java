package com.ezddd.core.aggregate;

import com.ezddd.core.annotation.EzIdentifier;
import com.ezddd.core.spring.EzBeanFactoryPostProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.lang.reflect.Field;

public class AggregateWrapper<T> extends AbstractAggregate {
    private static final Logger log = LoggerFactory.getLogger(EzBeanFactoryPostProcessor.class);
    private T concreteComponent;

    public AggregateWrapper (T concreteComponent) {
        Assert.notNull(concreteComponent, "concreteComponent must not be null.");
        this.concreteComponent = concreteComponent;
        this.identifier = resolveIdentifier(concreteComponent);
        this.version = 1L;
    }

    private String resolveIdentifier(T concreteComponent) {
        Class<?> concreteComponentType = concreteComponent.getClass();
        Field[] fields = concreteComponentType.getFields();
        if (fields == null && fields.length == 0) {
            throw new IllegalArgumentException("concreteComponent should have identifier field.");
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
            identifierField.setAccessible(true);
            try {
                String identifier = identifierField.get(concreteComponent).toString();
                return identifier;
            } catch (IllegalAccessException e) {
                log.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }
        }

    }
}
