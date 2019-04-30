package com.ezshop.domain.aggregate;

import com.ezddd.common.annotation.EzCommandHandler;
import com.ezddd.domain.annotation.EzAggregate;
import com.ezddd.domain.annotation.EzIdentifier;
import com.ezddd.domain.model.AggregateManager;
import com.ezshop.domain.aggregate.entity.BaseEntity;
import com.ezshop.domain.command.order.CreateOrderCmd;
import com.ezshop.domain.command.order.UpdateOrderCmd;

import java.time.Instant;

@EzAggregate
public class OrderAggrRoot extends BaseEntity {
    private static final long serialVersionUID = 5488832782823340677L;

    @EzIdentifier
    private String id;
    private Instant createTime;
    private String commodity;
    private String postAddress;

    @EzCommandHandler
    public OrderAggrRoot(CreateOrderCmd cmd) {
        AggregateManager.applyEvent("onOrderCreated", this, cmd);
    }

    @EzCommandHandler()
    public void updateOrder(UpdateOrderCmd cmd) {
        AggregateManager.applyEvent("onOrderUpdated", this, cmd);
    }
}
