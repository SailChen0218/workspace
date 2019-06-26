package com.ezshop.desensitize.type.impl;

import com.ezshop.desensitize.type.SensitiveType;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MobilePhoneType implements SensitiveType<String> {
    private static Logger logger = LoggerFactory.getLogger(MobilePhoneType.class);

    /**
     * 手机号格式校验正则
     */
    public static final String PHONE_REGEX = "^1(3[0-9]|4[57]|5[0-35-9]|7[0135678]|8[0-9])\\d{8}$";

    private boolean checkFormat(String mobilePhone) {
        if (mobilePhone == null || "".equals(mobilePhone)) {
            return false;
        } else {
            boolean isMatches =  mobilePhone.matches(PHONE_REGEX);
            if (!isMatches) {
                logger.error("手机号格式不正确。 mobilePhone[{}]", mobilePhone);
            }
            return isMatches;
        }
    }

    /**
     * 前三位，后四位，其他隐藏<例子:138******1234>
     * @param mobilePhone
     * @return
     */
    @Override
    public String desensitized(String mobilePhone) {
        if (!checkFormat(mobilePhone)) {
            return mobilePhone;
        } else {
            return StringUtils.left(mobilePhone, 3)
                    .concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(mobilePhone, 4),
                            StringUtils.length(mobilePhone), "*"), "***"));
        }
    }

    public static void main(String[] args) {
        MobilePhoneType mobilePhoneType = new MobilePhoneType();
        System.out.println(mobilePhoneType.desensitized("13116852368"));
    }
}
