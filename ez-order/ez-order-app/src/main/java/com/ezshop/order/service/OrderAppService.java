package com.ezshop.order.service;

import com.ezddd.core.annotation.EzAppService;
import com.ezddd.core.annotation.EzCommandMapping;
import com.ezddd.core.annotation.EzQueryMapping;
import com.ezddd.core.annotation.EzRemoting;
import com.ezddd.core.appservice.AbstractAppService;
import com.ezddd.core.command.query.QueryParameter;
import com.ezddd.core.command.query.constraints.Constraint;
import com.ezddd.core.command.query.constraints.ConstraintType;
import com.ezddd.core.response.AppResult;
import com.ezshop.domain.command.order.CreateOrderCmd;
import com.ezshop.domain.command.order.DeleteOrderCmd;
import com.ezshop.domain.command.order.UpdateOrderCmd;
import com.ezshop.domain.query.OrderAggrootQuery;
import com.ezshop.domain.query.param.OrderListParam;
import com.ezshop.domain.vo.OrderAggrootVo;
import com.ezshop.order.constants.BizCode;
import org.springframework.util.CollectionUtils;

import java.util.List;

@EzAppService(bizCode = BizCode.ORDER)
public class OrderAppService extends AbstractAppService {

    @EzRemoting
    OrderAggrootQuery orderAggrootQuery;

    @EzCommandMapping
    public AppResult<?> createOrder(CreateOrderCmd cmd) {
        return send(cmd, "I0001", "E0001");
    }

    @EzCommandMapping
    public AppResult<?> updateOrder(UpdateOrderCmd cmd) {
        return send(cmd, "I0002", "E0002");
    }

    @EzCommandMapping
    public AppResult<?> deleteOrder(DeleteOrderCmd cmd) {
        return send(cmd, "I0003", "E0003");
    }

    @EzQueryMapping
    public AppResult<List<OrderAggrootVo>> queryOrderAggrootList() {
        List<OrderAggrootVo> orderAggrootVoList = orderAggrootQuery.queryOrderAggrootList();
        if (CollectionUtils.isEmpty(orderAggrootVoList)) {
            return AppResult.valueOfSuccess("W0001");
        } else {
            return AppResult.valueOfSuccess(orderAggrootVoList, "I0004", orderAggrootVoList.size());
        }
    }

    @EzQueryMapping
    public AppResult<OrderAggrootVo> queryOrderAggrootById(
            @QueryParameter(value = "orderId", constraints = {
                    @Constraint(type = ConstraintType.NotNull, message = "${V0001}")
            }) String orderId,
            @QueryParameter(value = "commodity", constraints = {
                    @Constraint(type = ConstraintType.NotNull, message = "${V0001}")
            }) String commodity) {
        OrderAggrootVo orderAggrootVo = orderAggrootQuery.queryOrderAggrootById(orderId);
        if (orderAggrootVo == null) {
            return AppResult.valueOfSuccess("W0001");
        } else {
            return AppResult.valueOfSuccess(orderAggrootVo, "I0005");
        }
    }

    @EzQueryMapping
    public AppResult<OrderAggrootVo> queryOrderAggroot(OrderListParam param) {
        OrderAggrootVo orderAggrootVo = orderAggrootQuery.queryOrderAggrootById(param.getOrderId());
        if (orderAggrootVo == null) {
            return AppResult.valueOfSuccess("W0001");
        } else {
            return AppResult.valueOfSuccess(orderAggrootVo, "I0005");
        }
    }
}
