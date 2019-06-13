package com.ezddd.core.utils;

import cn.com.jdls.foundation.startup.AppEnv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class AppEnvUtil {
    private static Logger log = LoggerFactory.getLogger(AppEnvUtil.class);

    public static <T> T getParameter(String key, T defaultValue) {
        if (defaultValue == null) {
            throw new IllegalArgumentException("defaultValue must not be null.");
        }

        String value = AppEnv.getParameter(key);
        if (StringUtils.isEmpty(value)) {
            return defaultValue;
        }

        Class<?> clazz = defaultValue.getClass();
        try {
            if (Boolean.class.isAssignableFrom(clazz)) {
                return (T) Boolean.valueOf(value);
            }

            if (String.class.isAssignableFrom(clazz)) {
                return (T) value;
            }

            if (Integer.class.isAssignableFrom(clazz)) {
                return (T) Integer.valueOf(value);
            } else if (Long.class.isAssignableFrom(clazz)) {
                return (T) Long.valueOf(value);
            } else if (Short.class.isAssignableFrom(clazz)) {
                return (T) Short.valueOf(value);
            } else if (Double.class.isAssignableFrom(clazz)) {
                return (T) Double.valueOf(value);
            } else if (Float.class.isAssignableFrom(clazz)) {
                return (T) Float.valueOf(value);
            } else {
                return defaultValue;
            }
        } catch (Exception ex) {
            return defaultValue;
        }
    }

    public static <T extends Boolean> Boolean getBooleanParameter(String key, T defaultValue) {
        String value = AppEnv.getParameter(key);
        if (StringUtils.isEmpty(value)) {
            return defaultValue;
        } else {
            try {
                return Boolean.parseBoolean(value);
            } catch (Exception ex) {
                return defaultValue;
            }
        }
    }

    public static <T extends Number> Number getNumberParameter(String key, T defaultValue) {
        String value = AppEnv.getParameter(key);
        if (StringUtils.isEmpty(value)) {
            return defaultValue;
        } else {
            try {
                Class<?> clazz = defaultValue.getClass();
                if (Integer.class.isAssignableFrom(clazz)) {
                    return Integer.parseInt(value);
                } else if (Long.class.isAssignableFrom(clazz)) {
                    return Long.parseLong(value);
                } else if (Short.class.isAssignableFrom(clazz)) {
                    return Short.parseShort(value);
                } else if (Double.class.isAssignableFrom(clazz)) {
                    return Double.parseDouble(value);
                } else if (Float.class.isAssignableFrom(clazz)) {
                    return Float.parseFloat(value);
                } else {
                    return defaultValue;
                }
            } catch (Exception ex) {
                log.error("get parameter failed. key:" + key + " use default value:" + defaultValue, ex);
                return defaultValue;
            }
        }
    }


}
