package com.ezshop.order.service;

import com.ezddd.app.service.AbstractAppService;
import com.ezddd.common.annotation.EzAppBizDetail;
import com.ezddd.common.annotation.EzAppService;
import com.ezddd.common.response.AppResult;
import com.ezshop.order.command.HelloWorldCmd;
import com.ezshop.order.constants.BizCodeCST;
import com.ezshop.order.constants.BizDetailCodeCST;

@EzAppService(bizCode = BizCodeCST.ORDER)
public class OrderAppService extends AbstractAppService {

    @EzAppBizDetail(bizDetailCode = BizDetailCodeCST.ORDER_HELLOWORLD)
    public AppResult<String> helloWorld(HelloWorldCmd helloWorldCmd) {
        return send(helloWorldCmd);
    }

}
