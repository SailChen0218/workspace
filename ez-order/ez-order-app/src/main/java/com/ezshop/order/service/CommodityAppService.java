package com.ezshop.order.service;

import com.ezddd.core.annotation.EzAppService;
import com.ezddd.core.annotation.EzCommandMapping;
import com.ezddd.core.appservice.AbstractAppService;
import com.ezddd.core.response.AppResult;
import com.ezshop.domain.command.commodity.CreateCommodityCmd;
import com.ezshop.order.constants.BizCode;

@EzAppService(bizCode = BizCode.COMMODITY)
public class CommodityAppService extends AbstractAppService {

    @EzCommandMapping
    public AppResult<?> createCommodity(CreateCommodityCmd cmd) {
        return send(cmd, "I0001", "E0001");
    }
}
