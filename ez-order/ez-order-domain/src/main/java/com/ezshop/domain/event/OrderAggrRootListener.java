package com.ezshop.domain.event;

import com.ezddd.common.annotation.EzEventHandler;
import com.ezddd.domain.Event.AbstractEventListener;
import com.ezddd.domain.Event.EventArgs;
import com.ezshop.domain.aggregate.OrderAggrRoot;
import com.ezshop.domain.command.order.CreateOrderCmd;
import com.ezshop.domain.command.order.UpdateOrderCmd;

/**
 * <p>标题: </p>
 * <p>功能描述: </p>
 * <p>
 * <p>版权: 税友软件集团股份有限公司</p>
 * <p>创建时间: 2019/4/30</p>
 * <p>作者：cqf</p>
 * <p>修改历史记录：</p>
 * ====================================================================<br>
 */
public class OrderAggrRootListener extends AbstractEventListener {

    @EzEventHandler(eventSourcing = false)
    public void onOrderCreated(OrderAggrRoot sender, EventArgs<CreateOrderCmd> eventArgs) {
        CreateOrderCmd cmd = eventArgs.getArgs();
    }

    @EzEventHandler
    public void onOrderUpdated(OrderAggrRoot sender, EventArgs<UpdateOrderCmd> eventArgs) {
    }
}
