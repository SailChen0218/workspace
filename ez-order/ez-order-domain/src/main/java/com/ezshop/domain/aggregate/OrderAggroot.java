package com.ezshop.domain.aggregate;

import com.ezddd.core.aggregate.AggregateManager;
import com.ezddd.core.annotation.EzAggregate;
import com.ezddd.core.annotation.EzCommandHandler;
import com.ezddd.core.annotation.EzIdentifier;
import com.ezddd.core.utils.AggregateUtil;
import com.ezshop.domain.aggregate.entity.BaseEntity;
import com.ezshop.domain.command.order.CreateOrderCmd;
import com.ezshop.domain.command.order.DeleteOrderCmd;
import com.ezshop.domain.command.order.UpdateOrderCmd;
import lombok.Data;

@EzAggregate
@Data
public class OrderAggroot extends BaseEntity {
    private static final long serialVersionUID = 5488832782823340677L;

    @EzIdentifier
    private String orderId;
    private String commodity;
    private String postAddress;

    @EzCommandHandler
    public OrderAggroot(CreateOrderCmd cmd) throws Exception {
        this.orderId = AggregateUtil.generateID();
        this.commodity = cmd.getCommodity();
        this.postAddress = cmd.getPostAddress();
        this.version = 1L;
        this.createTime = System.currentTimeMillis();
        this.updateTime = System.currentTimeMillis();
        this.deleted = false;
        AggregateManager.apply("onOrderCreated", this, cmd);
    }

    @EzCommandHandler
    public void updateOrder(UpdateOrderCmd cmd) throws Exception {
        this.commodity = cmd.getCommodity();
        this.postAddress = cmd.getPostAddress();
        AggregateManager.apply("onOrderUpdated", this, cmd);
    }

    @EzCommandHandler
    public void deleteOrder(DeleteOrderCmd cmd) throws Exception {
        this.deleted = true;
        AggregateManager.apply("onOrderDeleted", this, cmd);
    }

}
