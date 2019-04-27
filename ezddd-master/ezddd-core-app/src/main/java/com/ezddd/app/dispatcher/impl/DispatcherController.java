package com.ezddd.app.dispatcher.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ezddd.app.dispatcher.AbstractDispatcher;
import com.ezddd.common.response.AppResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DispatcherController extends AbstractDispatcher {

    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public AppResult submit(@RequestBody String parameters) {
        JSONObject jsonObject = JSON.parseObject(parameters);
        Map<String, Object> parameterMap = jsonObject.getInnerMap();
        return doDispatch(parameterMap);
    }
}
