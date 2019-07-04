package com.ezshop.desensitize.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Properties;

public class MessageConvertor {
    private final static Logger log = LoggerFactory.getLogger(MessageConvertor.class);
    private static final Properties properties = new Properties();

    static {
        try {
            ClassLoader loader = ClassLoader.getSystemClassLoader();
            URL fileURL = loader.getResource("validator-message.properties");
            if (fileURL == null) {
                log.error("validator-message.properties配置文件不存在。");
            } else {
                InputStream inputStream = new FileInputStream(fileURL.getPath());
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(inputStream, "UTF-8"));
                properties.load(bufferedReader);
            }
        } catch (FileNotFoundException ex) {
            log.error("加载validator-message.properties配置文件失败。错误内容:{}", ex.getMessage());
        } catch (IOException ex) {
            log.error("读取validator-message.properties配置文件失败。错误内容:{}", ex.getMessage());
        }
    }

    private static boolean isMessageMetaData(String messageMetaData) {
        if (messageMetaData == null) return false;
        String pattern = "^[\\[](\\w+(.?\\w+)*)(,[{]([^,])*[}])*[\\]]$";
        return messageMetaData.matches(pattern);
    }

    public static String convert(String messageMetaData) {
        if (isMessageMetaData(messageMetaData)) {
            String metaData = messageMetaData.substring(1, messageMetaData.length() - 1);
            String[] splitMetaDatas = metaData.split(",\\{");
            String messageID = splitMetaDatas[0];
            String[] params = null;
            if (splitMetaDatas.length > 1) {
                params = new String[splitMetaDatas.length - 1];
                for (int i = 1; i < splitMetaDatas.length; i++) {
                    params[i - 1] = splitMetaDatas[i].substring(0, splitMetaDatas[i].length() - 1);
                }
            }
            String message = properties.getProperty(messageID);
            return MessageFormat.format(message, params);
        } else {
            return messageMetaData;
        }
    }

    public static void main(String[] args) {
        String messageMetaData = "[HGZX.I0001,{纳税人{sadas},{121231},{2019-09-09}]";
        System.out.println(convert(messageMetaData));
    }
}
