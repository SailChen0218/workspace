package com.ezshop.order.service;

import com.ezddd.app.service.AbstractAppService;
import com.ezddd.common.annotation.EzAppBizDetail;
import com.ezddd.common.annotation.EzAppService;
import com.ezddd.common.response.AppResult;
import com.ezshop.order.command.GoodsAddCmd;
import com.ezshop.order.constants.BizCodeCST;
import com.ezshop.order.constants.BizDetailCodeCST;

@EzAppService(bizCode = BizCodeCST.GOODS)
public class GoodsAppService extends AbstractAppService {

    @EzAppBizDetail(bizDetailCode = BizDetailCodeCST.GOODS_ADD)
    public AppResult<String> add(GoodsAddCmd goodsAddCmd) {
        return send(goodsAddCmd);
    }

}
