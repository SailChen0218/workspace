package com.ezddd.core.aggregate;

import com.ezddd.core.spring.EzBeanFactoryPostProcessor;
import com.ezddd.core.utils.AggregateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class AggregateWrapper<T> extends AbstractAggregate {
    private static final long serialVersionUID = -6678899096227606860L;
    private static final Logger log = LoggerFactory.getLogger(EzBeanFactoryPostProcessor.class);

    private T aggregateRoot;

    public AggregateWrapper (T aggregateRoot) {
        super();
        Assert.notNull(aggregateRoot, "aggregateRoot must not be null.");
        this.aggregateRoot = aggregateRoot;
        this.identifier = AggregateUtil.getIdentifierFrom(aggregateRoot);
        this.version = AggregateUtil.getVersionFrom(aggregateRoot);
    }

    public T getAggregateRoot() {
        return this.aggregateRoot;
    }

    public void setConcreteComponent(T concreteComponent) {
        this.aggregateRoot = concreteComponent;
    }
}
