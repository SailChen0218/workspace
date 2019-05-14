package com.ezshop.domain.event;

import com.ezddd.core.aggregate.Aggregate;
import com.ezddd.core.aggregate.AggregateWrapper;
import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.annotation.EzEventHandler;
import com.ezddd.core.event.AbstractEventListener;
import com.ezddd.core.event.Event;
import com.ezddd.core.repository.Repository;
import com.ezddd.core.repository.RepositoryProvider;
import com.ezddd.core.utils.IdentifierUtil;
import com.ezshop.domain.aggregate.OrderAggrRoot;
import com.ezshop.domain.command.order.CreateOrderCmd;
import org.springframework.beans.factory.annotation.Autowired;

@EzComponent
public class OrderAggrRootListener extends AbstractEventListener {

    @Autowired
    RepositoryProvider repositoryProvider;

    @EzEventHandler
    public void onOrderCreated(Event<OrderAggrRoot> event) {
        Repository<OrderAggrRoot> repository = repositoryProvider.repositoryFor(event.getSender().getClass());
        CreateOrderCmd cmd = event.getArgs();

        OrderAggrRoot orderAggrRoot = event.getSender();
        orderAggrRoot.setId(IdentifierUtil.generateID());
        orderAggrRoot.setCreateTime(event.getTimestamp());
        orderAggrRoot.setCommodity(cmd.getCommodity());
        orderAggrRoot.setPostAddress(cmd.getPostAddress());
        Aggregate<OrderAggrRoot> aggregate = new AggregateWrapper(orderAggrRoot);

        repository.add(aggregate);
    }

    @EzEventHandler
    public void onOrderUpdated(Event<OrderAggrRoot> event) {
    }

    @EzEventHandler
    public void onOrderDeleted(Event<OrderAggrRoot> event) {
    }
}
