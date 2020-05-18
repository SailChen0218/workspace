package com.ezshop.spring;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class EzListableBeanFactory extends DefaultListableBeanFactory {
    public EzListableBeanFactory(BeanFactory beanFactory){
        super(beanFactory);
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) throws BeanDefinitionStoreException {
        System.out.println("TTTTTTTTTTTTTTTT");
        try {
            TimeUnit.SECONDS.sleep(60);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.registerBeanDefinition(beanName, beanDefinition);
    }
}
