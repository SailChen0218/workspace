package com.ezddd.core.dispatcher.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ezddd.core.dispatcher.AbstractDispatcher;
import com.ezddd.core.response.AppResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class DispatcherController extends AbstractDispatcher {

    @RequestMapping(value = "/api/{bizCode}/{bizDetailCode}", method = RequestMethod.POST)
    public AppResult submit(@PathVariable String bizCode,
                            @PathVariable String bizDetailCode,
                            @RequestBody String data) {
        Map<String, Object> parameterMap = new HashMap<>(3);
        JSONObject jsonObject = JSON.parseObject(data);
        Map<String, Object> dataMap = jsonObject.getInnerMap();
        parameterMap.put("bizCode", bizCode);
        parameterMap.put("bizDetailCode", bizDetailCode);
        parameterMap.put("data", dataMap);
        return doDispatch(parameterMap);
    }
}
