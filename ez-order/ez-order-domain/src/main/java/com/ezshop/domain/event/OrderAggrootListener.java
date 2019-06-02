package com.ezshop.domain.event;

import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.annotation.EzEventHandler;
import com.ezddd.core.event.AbstractEventListener;
import com.ezddd.core.event.Event;
import com.ezshop.domain.aggregate.OrderAggroot;
import com.ezshop.domain.repository.OrderAggrootRepository;
import org.springframework.beans.factory.annotation.Autowired;

@EzComponent
public class OrderAggrootListener extends AbstractEventListener<OrderAggroot> {

    @Autowired
    OrderAggrootRepository orderAggrootRepository;

    @EzEventHandler
    public void onOrderCreated(Event<OrderAggroot> event) {
        OrderAggroot orderAggr = event.getSender();
        orderAggrootRepository.create(orderAggr);
    }

    @EzEventHandler
    public void onOrderUpdated(Event<OrderAggroot> event) {
        OrderAggroot orderAggroot = event.getSender();
        orderAggrootRepository.save(orderAggroot);
    }

    @EzEventHandler
    public void onOrderDeleted(Event<OrderAggroot> event) {
        orderAggrootRepository.removeByIdentifier(event.getIdentifier());
    }
}
