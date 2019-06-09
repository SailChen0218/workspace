package com.ezddd.core.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.validation.constraints.NotNull;

@RunWith(SpringJUnit4ClassRunner.class)
//@PropertySource("classpath:message-validator.properties")
@ConfigurationProperties(value = "classpath:validator-message.properties")
public class ValidationUtilTest {

    private class QuueryPrameters {
        @NotNull(message = "${V0001}")
        private String orderid;

        private QuueryPrameters() {
        }
    }

    @Test
    public void testValidate() {
        QuueryPrameters quueryPrameters = new QuueryPrameters();
        quueryPrameters.orderid = null;
        ValidationUtil.ValidResult validResult = ValidationUtil.validateBean(quueryPrameters);
        if (validResult.hasErrors()) {
            System.out.println(validResult.toString());
        }
        System.out.println("");
    }
}
