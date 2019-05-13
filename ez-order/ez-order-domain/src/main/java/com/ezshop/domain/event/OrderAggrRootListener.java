package com.ezshop.domain.event;

import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.annotation.EzEventHandler;
import com.ezddd.core.event.AbstractEventListener;
import com.ezddd.core.event.Event;
import com.ezddd.core.utils.IdentifierUtil;
import com.ezshop.domain.aggregate.OrderAggrRoot;
import com.ezshop.domain.command.order.CreateOrderCmd;

@EzComponent
public class OrderAggrRootListener extends AbstractEventListener {

    @EzEventHandler
    public void onOrderCreated(Event<OrderAggrRoot> event) {
        OrderAggrRoot orderAggrRoot = event.getSender();
        CreateOrderCmd cmd = event.getArgs();
        orderAggrRoot.setId(IdentifierUtil.generateID());
        orderAggrRoot.setCreateTime(event.getTimestamp());
        orderAggrRoot.setCommodity(cmd.getCommodity());
        orderAggrRoot.setPostAddress(cmd.getPostAddress());
    }

    @EzEventHandler
    public void onOrderUpdated(Event<OrderAggrRoot> event) {
    }

    @EzEventHandler
    public void onOrderDeleted(Event<OrderAggrRoot> event) {
    }
}
