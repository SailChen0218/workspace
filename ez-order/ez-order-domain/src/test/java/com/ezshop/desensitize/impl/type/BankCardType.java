package com.ezshop.desensitize.impl.type;

import com.ezshop.desensitize.SensitiveType;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class BankCardType implements SensitiveType<String> {
    private static Logger logger = LoggerFactory.getLogger(BankCardType.class);

    /**
     * 判断是否为银行卡号
     * @param bankCard
     * @return
     */
    private boolean isBankCard(String bankCard) {
        if (bankCard == null || "".equals(bankCard)) {
            return false;
        }

        if(bankCard.length() < 15 || bankCard.length() > 19) {
            return false;
        }

        char bit = getBankCardCheckCode(bankCard.substring(0, bankCard.length() - 1));
        if(bit == 'N'){
            return false;
        }

        return bankCard.charAt(bankCard.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     * @param nonCheckCodeBankCard
     * @return
     */
    private char getBankCardCheckCode(String nonCheckCodeBankCard){
        if(nonCheckCodeBankCard == null || nonCheckCodeBankCard.trim().length() == 0
                || !nonCheckCodeBankCard.matches("\\d+")) {
            //如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeBankCard.trim().toCharArray();
        int luhmSum = 0;
        for(int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if(j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char)((10 - luhmSum % 10) + '0');
    }

    /**
     * 前六位，后四位，其他用星号隐藏每位1个星号<例子:6222600**********1234>
     * @param bankCard
     * @return
     */
    @Override
    public String desensitized(String bankCard) {
        if (!isBankCard(bankCard)) {
            logger.error("银行卡号格式不正确。bankCard[{}]", bankCard);
            return bankCard;
        } else {
            return StringUtils.left(bankCard, 6).concat(
                    StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(bankCard, 4),
                            StringUtils.length(bankCard), "*"), "******"));
        }
    }

    public static void main(String[] args) {
        BankCardType bankCardType = new BankCardType();
        System.out.println(bankCardType.desensitized("6668561358765114"));
    }
}
