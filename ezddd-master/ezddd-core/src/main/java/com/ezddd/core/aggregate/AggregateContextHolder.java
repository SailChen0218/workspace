package com.ezddd.core.aggregate;

public abstract class AggregateContextHolder {
    private static final ThreadLocal<AggregateContext> aggregateContextHolder = new ThreadLocal();

    public AggregateContextHolder() {
    }

    public static void resetRequestAttributes() {
        aggregateContextHolder.remove();
    }

    public static void setAggregateContext(AggregateContext aggregateContext) {
        if (aggregateContext == null) {
            resetRequestAttributes();
        } else {
            aggregateContextHolder.set(aggregateContext);
        }
    }

    public static AggregateContext getAggregateContext() {
        return aggregateContextHolder.get();
    }

    public static AggregateContext currentAggregateContext() {
        AggregateContext aggregateContext = getAggregateContext();
        if (aggregateContext == null) {
//            aggregateContext = new AggregateContext();
            setAggregateContext(aggregateContext);
        }

        return aggregateContext;
    }
}
