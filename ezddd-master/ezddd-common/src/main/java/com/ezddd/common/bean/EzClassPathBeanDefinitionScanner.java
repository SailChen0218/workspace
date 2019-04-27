package com.ezddd.common.bean;

import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Set;

public class EzClassPathBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {

    public EzClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        super(registry, false);
    }

    /**
     * Calls the parent search that will search and register all the candidates.
     * Then the registered objects are post processed to set them as
     * MapperFactoryBeans
     */
    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        for (AnnotationTypeFilter annotationTypeFilter : EzAnnotationTypeFilter.annotationTypeFilter) {
            addIncludeFilter(annotationTypeFilter);
        }
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
        return beanDefinitions;
    }
}
