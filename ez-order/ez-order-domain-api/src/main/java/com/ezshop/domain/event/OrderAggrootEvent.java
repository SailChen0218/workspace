package com.ezshop.domain.event;

import com.ezddd.core.annotation.EzEvent;
import com.ezddd.core.event.AbstractEventDefinition;
import com.ezshop.domain.constants.DomainConstants;

@EzEvent(domain = DomainConstants.NAME)
public class OrderAggrootEvent extends AbstractEventDefinition {
    public static final OrderAggrootEvent onOrderCreated = new OrderAggrootEvent("onOrderCreated");
    public static final OrderAggrootEvent onOrderUpdated = new OrderAggrootEvent("onOrderUpdated");
    public static final OrderAggrootEvent onOrderDeleted =
            new OrderAggrootEvent("onOrderDeleted") {
                @Override
                public Boolean isEventSourcing() {
                    return false;
                }
            };

    private OrderAggrootEvent(String eventName) {
        super(eventName);
    }
}
