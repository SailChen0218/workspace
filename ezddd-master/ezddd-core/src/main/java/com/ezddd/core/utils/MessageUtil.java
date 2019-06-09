package com.ezddd.core.utils;

import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class MessageUtil {

    public static String getMessage(String code, Object[] params) {
        if (StringUtils.isEmpty(code)) {
            throw new IllegalArgumentException("message code must not be empty.");
        }

        String message = MessageReader.instance.getMessage(code);
        if (StringUtils.isEmpty(message)) {
            throw new IllegalArgumentException("message not found. code:" + code);
        }

        return MessageFormat.format(message, params);
    }

    private static final class MessageReader {
        private final Map<String, String> messageHolder = new HashMap<>();

        private MessageReader() {
            loadMessage();
        }

        private void loadMessage() {
            Properties properties = new Properties();
            BufferedReader in = null;
            try {
                URL url = MessageUtil.class.getClassLoader().getResource("message.properties");
                in = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
                properties.load(in);
                Iterator<String> it = properties.stringPropertyNames().iterator();
                while (it.hasNext()) {
                    String key = it.next();
                    String value = properties.getProperty(key);
                    messageHolder.put(key, value);
                }
            } catch (Exception e) {
                System.out.println(e);
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                    }
                }
            }
        }

        private String getMessage(String code) {
            if (messageHolder.containsKey(code)) {
                return messageHolder.get(code);
            } else {
                return null;
            }
        }

        private static final MessageReader instance = new MessageReader();
    }
}
