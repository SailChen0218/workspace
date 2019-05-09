package com.ezshop.domain.aggregate;

import com.ezddd.core.aggregate.AggregateFactory;
import com.ezddd.core.aggregate.AggregateManager;
import com.ezddd.core.annotation.EzAggregate;
import com.ezddd.core.annotation.EzCommandHandler;
import com.ezddd.core.annotation.EzIdentifier;
import com.ezddd.core.event.EventType;
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
        AggregateManager.applyEvent("onOrderCreated", this, cmd, EventType.CREATED);
    }

    @EzCommandHandler
    public void updateOrder(UpdateOrderCmd cmd) {
        AggregateManager.applyEvent("onOrderUpdated", this, cmd, EventType.UPDATED);
    }

    public static class Factory implements AggregateFactory<OrderAggrRoot> {
        public static OrderAggrRoot createAggregate(CreateOrderCmd cmd) {
            return new OrderAggrRoot(cmd);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public String getCommodity() {
        return commodity;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }

    public String getPostAddress() {
        return postAddress;
    }

    public void setPostAddress(String postAddress) {
        this.postAddress = postAddress;
    }
}
