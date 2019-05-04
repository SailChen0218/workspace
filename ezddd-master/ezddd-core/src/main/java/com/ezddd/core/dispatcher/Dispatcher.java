package com.ezddd.core.dispatcher;


import com.ezddd.core.response.AppResult;

import java.util.Map;

public interface Dispatcher {
    AppResult doDispatch(Map<String, Object> parameters);
}
