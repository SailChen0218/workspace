package com.ezddd.app.dispatcher;


import com.ezddd.common.response.AppResult;

import java.util.Map;

public interface Dispatcher {
    AppResult doDispatch(Map<String, Object> parameters);
}
