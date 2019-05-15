package com.ezshop.domain.aggregate;

import com.ezddd.core.aggregate.AggregateFactory;
import com.ezddd.core.annotation.EzCommandHandler;
import com.ezddd.core.annotation.EzIdentifier;
import com.ezddd.core.event.Event;
import com.ezshop.domain.aggregate.entity.BaseEntity;

import java.time.Instant;

public class GoodsAggrRoot extends BaseEntity {
    private static final long serialVersionUID = 5488832782823340677L;

    @EzIdentifier
    private String id;
    private Instant createTime;
    private String commodity;
    private String postAddress;

    @EzCommandHandler
    public GoodsAggrRoot() {
//        AggregateManager.applyEvent("onGoodsCreated", this, cmd, EventType.CREATED);
    }

//    @EzCommandHandler
//    public void updateOrder(UpdateOrderCmd cmd) {
//        AggregateManager.applyEvent("onGoodsUpdated", this, cmd, EventType.UPDATED);
//    }

    public static class Factory implements AggregateFactory<GoodsAggrRoot> {
        public static GoodsAggrRoot create(String aggregateIdentifier, Event<GoodsAggrRoot> firstEvent) {
            return new GoodsAggrRoot();
        }
    }

}
