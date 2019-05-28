package com.ezshop.domain.aggregate;

import com.ezddd.core.aggregate.AggregateManager;
import com.ezddd.core.annotation.EzAggregate;
import com.ezddd.core.annotation.EzCommandHandler;
import com.ezddd.core.annotation.EzIdentifier;
import com.ezshop.domain.aggregate.entity.BaseEntity;
import com.ezshop.domain.command.order.CreateOrderCmd;
import com.ezshop.domain.command.order.UpdateOrderCmd;

@EzAggregate
public class OrderAggroot extends BaseEntity {
    private static final long serialVersionUID = 5488832782823340677L;

    @EzIdentifier
    private String orderId;
    private String commodity;
    private String postAddress;

    @EzCommandHandler
    public OrderAggroot(CreateOrderCmd cmd) throws Exception {
        AggregateManager.apply("onOrderCreated", this, cmd);
    }

    @EzCommandHandler
    public void updateOrder(UpdateOrderCmd cmd) throws Exception{
        AggregateManager.apply("onOrderUpdated", this, cmd);
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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
