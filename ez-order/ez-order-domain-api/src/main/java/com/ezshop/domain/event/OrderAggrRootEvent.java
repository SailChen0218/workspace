package com.ezshop.domain.event;

import com.ezddd.core.annotation.EzEvent;
import com.ezddd.core.event.AbstractEventDefinition;

@EzEvent(domain = "Order")
public class OrderAggrRootEvent extends AbstractEventDefinition {
    public static final OrderAggrRootEvent onOrderCreated = new OrderAggrRootEvent("onOrderCreated");
    public static final OrderAggrRootEvent onOrderUpdated = new OrderAggrRootEvent("onOrderUpdated");
    public static final OrderAggrRootEvent onOrderDeleted =
            new OrderAggrRootEvent("onOrderDeleted") {
                @Override
                public boolean isEventSourcing() {
                    return false;
                }
            };

    private OrderAggrRootEvent(String eventName) {
        super(eventName);
    }
}
