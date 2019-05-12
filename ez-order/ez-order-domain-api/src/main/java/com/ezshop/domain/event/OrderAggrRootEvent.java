package com.ezshop.domain.event;

import com.ezddd.core.annotation.EzEvent;
import com.ezddd.core.event.AbstractEventDefinition;
import com.ezddd.core.event.EventType;

@EzEvent(domain = "Order")
public class OrderAggrRootEvent extends AbstractEventDefinition {
    public static final OrderAggrRootEvent ON_ORDER_CREATED =
            new OrderAggrRootEvent("onOrderCreated", EventType.CREATED);

    public static final OrderAggrRootEvent ON_ORDER_UPDATED =
            new OrderAggrRootEvent("onOrderUpdated", EventType.UPDATED);

    public static final OrderAggrRootEvent ON_ORDER_DELETED =
            new OrderAggrRootEvent("onOrderDeleted", EventType.DELETED) {
                @Override
                public boolean isEventSourcing() {
                    return false;
                }
            };

    private OrderAggrRootEvent(String eventName, int eventType) {
        super(eventName, eventType);
    }
}
