package com.ezddd.core.spring;

import com.ezddd.core.annotation.EzComponent;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;

@EzComponent
public class EzBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
//        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) registry;
//        AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(registry);
//        String[] beanNames = beanFactory.getBeanNamesForAnnotation(EzComponent.class);
//        if (beanNames != null && beanNames.length > 0) {
//            for (int i = 0; i < beanNames.length; i++) {
//                GenericBeanDefinition beanDefinition =
//                        (GenericBeanDefinition)beanFactory.getBeanDefinition(beanNames[i]);
//                reader.registerBean(beanDefinition.getBeanClass());
//            }
//        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }
}
