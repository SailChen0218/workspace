package com.ezddd.core.spring;

import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Set;

public class EzClassPathBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {
    private AnnotationTypeFilter[] annotationTypeFilter;

    public EzClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry,
                                            AnnotationTypeFilter[] annotationTypeFilter) {
        super(registry, true);
        this.annotationTypeFilter = annotationTypeFilter;
    }

    /**
     * Calls the parent search that will search and register all the candidates.
     * Then the registered objects are post processed to set them as
     * MapperFactoryBeans
     */
    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        for (AnnotationTypeFilter annotationTypeFilter : annotationTypeFilter) {
            addIncludeFilter(annotationTypeFilter);
        }
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
        return beanDefinitions;
    }
}
