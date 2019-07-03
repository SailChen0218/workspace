package com.ezshop.desensitize.impl.type;

import com.ezshop.desensitize.SensitiveType;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class EmailType implements SensitiveType<String> {
    private static Logger logger = LoggerFactory.getLogger(MobilePhoneType.class);

    /**
     * email格式校验正则
     */
    private static final String EMAIL_REGEX = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)" +
            "+[a-zA-Z]{2,}$";

    private boolean isEmail(String email) {
        if (email == null || "".equals(email)) {
            return false;
        } else {
            boolean isMatches =  email.matches(EMAIL_REGEX);
            if (!isMatches) {
                logger.error("Email格式不正确。 email[{}]", email);
            }
            return isMatches;
        }
    }

    /**
     * 邮箱前缀仅显示第一个字母，前缀其他隐藏，用星号代替，@及后面的地址显示<例子:g**@163.com>
     * @param email
     * @return
     */
    @Override
    public String desensitized(String email) {
        if (!isEmail(email)) {
            return email;
        } else {
            int index = StringUtils.indexOf(email, "@");
            return StringUtils.rightPad(StringUtils.left(email, 1), index, "*")
                    .concat(StringUtils.mid(email, index, StringUtils.length(email)));
        }
    }

    public static void main(String[] args) {
        EmailType emailType = new EmailType();
        System.out.println(emailType.desensitized("1123@servyou.com.cn"));
    }
}
