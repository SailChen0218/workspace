package com.ezshop.spring;

import com.ezshop.annotations.EzExtension;
import com.ezshop.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

@Component
public class EzBeanFactoryPostProcessor implements BeanFactoryPostProcessor, ApplicationContextAware {

    private ApplicationContext applicationContext;
    private String area;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // 删除区域匹配Bean信息
        removeAreaUnmatchBeanDefinition(beanFactory);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.area = applicationContext.getEnvironment().getProperty("area");
        this.applicationContext = applicationContext;
    }

    /**
     *
     */
    private void removeAreaUnmatchBeanDefinition(ConfigurableListableBeanFactory beanFactory) {
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory)beanFactory;
        String[] beanNames = beanFactory.getBeanNamesForAnnotation(EzExtension.class);
        for (String beanName : beanNames) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
            try {
                Class<?> clazz = Class.forName(beanDefinition.getBeanClassName());

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }



        for (String beanName : beanFactory.getBeanDefinitionNames()) {
            if ("HBUserServiceImpl".equals(beanName)) {
                BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
                System.out.println(beanDefinition);

                try {
                    Class<?> clazz = Class.forName(beanDefinition.getBeanClassName());
                    Annotation[] annotations = clazz.getAnnotations();
                    for (Annotation annotation: annotations) {
                        if (annotation instanceof EzExtension) {
                            if (!area.equals(((EzExtension) annotation).area())) {
                                defaultListableBeanFactory.removeBeanDefinition(beanName);
                            }
//                            applicationContext.getBeansOfType(IUserService.class);
//
//                            System.out.println(((EzExtension) annotation).area());
//
//                            defaultListableBeanFactory.removeBeanDefinition(beanName);
//                            defaultListableBeanFactory.getBeanNamesForType(IUserService.class);
                        }
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
