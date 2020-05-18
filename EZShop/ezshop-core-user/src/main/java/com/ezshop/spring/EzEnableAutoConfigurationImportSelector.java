package com.ezshop.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigurationImportSelector;


public class EzEnableAutoConfigurationImportSelector extends AutoConfigurationImportSelector {
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        super.setBeanFactory(beanFactory);
    }
}
