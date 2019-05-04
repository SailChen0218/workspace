package com.ezshop.order.service;

import com.ezddd.core.annotation.EzAppBizDetail;
import com.ezddd.core.annotation.EzAppService;
import com.ezddd.core.appservice.AbstractAppService;
import com.ezddd.core.response.AppResult;
import com.ezshop.domain.command.order.CreateOrderCmd;
import com.ezshop.order.constants.BizCodeCST;
import com.ezshop.order.constants.BizDetailCodeCST;

@EzAppService(bizCode = BizCodeCST.ORDER)
public class OrderAppService extends AbstractAppService {

    @EzAppBizDetail(bizDetailCode = BizDetailCodeCST.ORDER_CREATE)
    public AppResult<String> createOrder(CreateOrderCmd cmd) {
        return send(cmd);
    }
}
