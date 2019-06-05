package com.ezshop.order.service;

import com.ezddd.core.annotation.EzAppMapping;
import com.ezddd.core.annotation.EzAppService;
import com.ezddd.core.appservice.AbstractAppService;
import com.ezddd.core.response.AppResult;
import com.ezshop.domain.command.commodity.CreateCommodityCmd;
import com.ezshop.order.constants.BizCode;
import com.ezshop.order.constants.BizDetailCode;

@EzAppService(bizCode = BizCode.COMMODITY)
public class CommodityAppService extends AbstractAppService {

    @EzAppMapping(bizDetailCode = BizDetailCode.CREATE)
    public AppResult<?> createCommodity(CreateCommodityCmd cmd) {
        return send(cmd, "I0001", "E0001", null);
    }

//    @EzAppMapping(bizDetailCode = BizDetailCode.UPDATE)
//    public AppResult<?> updateOrder(UpdateOrderCmd cmd) {
//        return send(cmd, "I0002", "E0002", null);
//    }
//
//    @EzAppMapping(bizDetailCode = BizDetailCode.DELETE)
//    public AppResult<?> deleteOrder(DeleteOrderCmd cmd) {
//        return send(cmd, "I0003", "E0003", null);
//    }
}
