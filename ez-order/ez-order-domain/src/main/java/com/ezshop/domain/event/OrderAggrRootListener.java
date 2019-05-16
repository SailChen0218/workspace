package com.ezshop.domain.event;

import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.annotation.EzEventHandler;
import com.ezddd.core.event.AbstractEventListener;
import com.ezddd.core.event.Event;
import com.ezddd.core.utils.IdentifierUtil;
import com.ezshop.domain.aggregate.OrderAggrRoot;
import com.ezshop.domain.command.order.CreateOrderCmd;
import com.ezshop.domain.command.order.UpdateOrderCmd;

@EzComponent
public class OrderAggrRootListener<T extends OrderAggrRoot> extends AbstractEventListener {

    @EzEventHandler
    public void onOrderCreated(Event<T> event) {
        CreateOrderCmd cmd = event.getArgs();
        OrderAggrRoot orderAggrRoot = event.getSender();
        orderAggrRoot.setId(IdentifierUtil.generateID());
        orderAggrRoot.setCommodity(cmd.getCommodity());
        orderAggrRoot.setPostAddress(cmd.getPostAddress());
        repository.create(orderAggrRoot);
    }

    @EzEventHandler
    public void onOrderUpdated(Event<T> event) {
        UpdateOrderCmd cmd = event.getArgs();
        OrderAggrRoot orderAggrRoot = event.getSender();
        orderAggrRoot.setCommodity(cmd.getCommodity());
        orderAggrRoot.setPostAddress(cmd.getPostAddress());
        repository.update(orderAggrRoot);
    }

    @EzEventHandler
    public void onOrderDeleted(Event<T> event) {
        repository.remove(event.getSender().getId());
    }
}
