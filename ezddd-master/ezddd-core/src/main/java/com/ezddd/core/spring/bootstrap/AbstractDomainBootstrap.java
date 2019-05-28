package com.ezddd.core.spring.bootstrap;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.GenericApplicationContext;

public class AbstractDomainBootstrap {
    protected static class CustomAppCtxInitializer implements ApplicationContextInitializer<GenericApplicationContext> {
        @Override
        public void initialize(GenericApplicationContext applicationContext) {
            applicationContext
                    .getDefaultListableBeanFactory()
                    .setAllowBeanDefinitionOverriding(false);
        }
    }
}
