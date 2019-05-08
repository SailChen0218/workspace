package com.ezshop.domain.event;

import com.ezddd.core.event.AbstractEventListener;
import com.ezddd.core.event.Event;
import com.ezddd.core.event.EventStore;
import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.annotation.EzEventHandler;

import com.ezddd.core.response.Result;
import com.ezshop.domain.aggregate.OrderAggrRoot;

@EzComponent
public class OrderAggrRootListener extends AbstractEventListener {

    EventStore eventStore;

    @EzEventHandler(eventSourcing = false)
    public void onOrderCreated(Event<OrderAggrRoot> event) {
    }

    @EzEventHandler
    public void onOrderUpdated(Event<OrderAggrRoot> event) {
    }
}
