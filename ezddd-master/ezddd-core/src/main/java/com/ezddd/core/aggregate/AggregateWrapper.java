package com.ezddd.core.aggregate;

import com.ezddd.core.spring.EzBeanFactoryPostProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class AggregateWrapper<T> extends AbstractAggregate {
    private static final Logger log = LoggerFactory.getLogger(EzBeanFactoryPostProcessor.class);
    private T concreteComponent;

    public AggregateWrapper (T concreteComponent) {
        super();

        Assert.notNull(concreteComponent, "concreteComponent must not be null.");
        this.concreteComponent = concreteComponent;
    }

    public T getConcreteComponent() {
        return this.concreteComponent;
    }

}
