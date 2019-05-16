package com.ezshop.domain.event;

import com.ezddd.core.aggregate.Aggregate;
import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.annotation.EzEventHandler;
import com.ezddd.core.event.AbstractEventListener;
import com.ezddd.core.event.Event;
import com.ezddd.core.repository.Repository;
import com.ezddd.core.utils.IdentifierUtil;
import com.ezshop.domain.aggregate.OrderAggrRoot;
import com.ezshop.domain.command.order.CreateOrderCmd;
import com.ezshop.domain.command.order.UpdateOrderCmd;

import javax.annotation.PostConstruct;

@EzComponent
public class OrderAggrRootListener extends AbstractEventListener {
    private Repository<OrderAggrRoot> repository = null;

    @PostConstruct
    public void init() {
        Repository<OrderAggrRoot> repository = repositoryProvider.repositoryFor(OrderAggrRoot.class);
    }

    @EzEventHandler
    public void onOrderCreated(Event<OrderAggrRoot> event) {
        CreateOrderCmd cmd = event.getArgs();
        OrderAggrRoot orderAggrRoot = event.getSender();
        orderAggrRoot.setId(IdentifierUtil.generateID());
        orderAggrRoot.setCreateTime(event.getTimestamp());
        orderAggrRoot.setCommodity(cmd.getCommodity());
        orderAggrRoot.setPostAddress(cmd.getPostAddress());
        repository.addWithWrapper(orderAggrRoot);
    }

    @EzEventHandler
    public void onOrderUpdated(Event<OrderAggrRoot> event) {
        UpdateOrderCmd cmd = event.getArgs();
        OrderAggrRoot orderAggrRoot = event.getSender();
        Aggregate<OrderAggrRoot> orderAggrRootAggregate = repository.load(cmd.getId());
    }

    @EzEventHandler
    public void onOrderDeleted(Event<OrderAggrRoot> event) {
    }
}
