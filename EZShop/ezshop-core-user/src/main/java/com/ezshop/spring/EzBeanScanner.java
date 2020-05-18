package com.ezshop.spring;

import com.ezshop.annotations.EzExtension;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import java.util.Set;

//@Component
public class EzBeanScanner extends ClassPathBeanDefinitionScanner {

    public EzBeanScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }

    @Override
    protected void registerDefaultFilters() {
        super.registerDefaultFilters();
        this.addIncludeFilter(new AnnotationTypeFilter(EzExtension.class));
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
        return super.doScan(basePackages);
    }
}
