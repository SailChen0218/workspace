package com.ezddd.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * <p>标题: 系统配置文件读取工具</p>
 * <p>功能描述: </p>
 * <p>
 * <p>版权: 税友软件集团股份有限公司</p>
 * <p>创建时间: 2019/3/25</p>
 * <p>作者：cqf</p>
 * <p>修改历史记录：</p>
 * ====================================================================<br>
 */
public class AppPropertyUtil {
    private static final Properties properties = new Properties();
    private final static Logger LOG = LoggerFactory.getLogger(AppPropertyUtil.class);

    static {
        try {
            String appPath = System.getProperty("application.path");
            FileReader reader = new  FileReader(appPath + "/application.properties");
            properties.load(reader);
        } catch (FileNotFoundException ex) {
            LOG.error("加载application.properties配置文件失败。错误内容:{}",ex.getMessage());
        } catch (IOException ex) {
            LOG.error("读取application.properties配置文件失败。错误内容:{}",ex.getMessage());
        }
    }

    public static String getProperty(String key, String defaultVal){
        return properties.getProperty(key, defaultVal);
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
