package com.ezshop.desensitize.type.impl;

import com.ezshop.desensitize.type.SensitiveType;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ChineseNameType implements SensitiveType<String> {
    private static Logger logger = LoggerFactory.getLogger(ChineseNameType.class);
    private static final String regEx = "[\u4E00-\u9FA5]{2,5}(?:·[\u4E00-\u9FA5]{2,5})*";

    private boolean isChineseName(String fullName){
        if (fullName == null || "".equals(fullName)) {
            return false;
        }

        boolean isMatches = Pattern.matches(regEx, fullName);
        if (!isMatches) {
            logger.error("姓名格式不正确。 mobilePhone[{}]", fullName);
        }
        return isMatches;
    }

    /**
     * 只显示第一个汉字，其他隐藏为2个星号<例子：李**>
     * @param fullName
     * @return
     */
    @Override
    public String desensitized(String fullName) {
        if (!isChineseName(fullName)) {
            return fullName;
        } else {
            String name = StringUtils.left(fullName, 1);
            return StringUtils.rightPad(name, StringUtils.length(fullName), "*");
        }
    }

    public static void main(String[] args) {
        ChineseNameType chineseNameType = new ChineseNameType();
        System.out.println(chineseNameType.desensitized("中国人"));
    }
}
