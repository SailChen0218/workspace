package com.ezshop.domain.event;

import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.annotation.EzEventHandler;
import com.ezddd.core.event.AbstractEventListener;
import com.ezddd.core.event.Event;
import com.ezshop.domain.aggregate.OrderAggroot;
import com.ezshop.domain.command.order.UpdateOrderCmd;
import com.ezshop.domain.repository.OrderAggrRepository;
import org.springframework.beans.factory.annotation.Autowired;

@EzComponent
public class OrderAggrootListener extends AbstractEventListener<OrderAggroot> {

    @Autowired
    OrderAggrRepository orderAggrRepository;

    @EzEventHandler
    public void onOrderCreated(Event<OrderAggroot> event) throws Exception {
        OrderAggroot orderAggr = event.getSender();
        orderAggrRepository.create(orderAggr);
    }

    @EzEventHandler
    public void onOrderUpdated(Event<OrderAggroot> event) throws Exception {
        OrderAggroot orderAggroot = event.getSender();
        orderAggrRepository.save(orderAggroot);
    }

    @EzEventHandler
    public void onOrderDeleted(Event<OrderAggroot> event) throws Exception {
        orderAggrRepository.removeByIdentifier(event.getIdentifier());
    }
}
