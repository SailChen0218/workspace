package com.ezshop.desensitize.impl.type;

import com.ezshop.desensitize.SensitiveType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class AddressType implements SensitiveType<String> {
    /**
     * 隐藏字符串长度
     */
    private static final int SENSITIVE_SIZE = 20;

    /**
     * 隐藏指定长度的字符串<例子：北京市海淀区****>
     * @param address
     * @return
     */
    @Override
    public String desensitized(String address) {
        if (address == null || "".equals(address)) {
            return address;
        } else {
            int length = StringUtils.length(address);
            return StringUtils.rightPad(StringUtils.left(address, length - SENSITIVE_SIZE), length, "*");
        }
    }

    public static void main(String[] args) {
        AddressType addressType = new AddressType();
        System.out.println(addressType.desensitized("北京市海淀区曙光路1778号3幢14-09"));
    }
}
