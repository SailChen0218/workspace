package com.ezddd.app.message;

import com.ezddd.common.annotation.EzComponent;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@EzComponent
public class MessageCode {

    @PostConstruct
    public void init() {

    }

    private static final Map<String, String> messages = new HashMap<>();
    public static String getMessage(String code) {
        return messages.get(code);
    }

}
