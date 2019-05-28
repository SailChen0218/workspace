package com.ezddd.core.spring;

import com.ezddd.core.annotation.EzAppService;
import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.annotation.EzDomainService;
import com.ezddd.core.annotation.EzRemoting;
import com.ezddd.core.remote.RemoteProxyFactory;
import com.ezddd.core.utils.ClassUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@EzComponent
public class EzInstantiationAwareBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {
    private static final Logger log = LoggerFactory.getLogger(EzBeanFactoryPostProcessor.class);
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return populateAutowiredProperty(bean, beanName);
    }

    private Object populateAutowiredProperty(Object bean, String beanName) {
        try {
            if (!hasEzAnnotation(bean)) {
                return bean;
            }

            DefaultListableBeanFactory beanFactory =
                    (DefaultListableBeanFactory)applicationContext.getAutowireCapableBeanFactory();
            List<Field> fieldList = new ArrayList<>();
            ClassUtil.findFiledsIncludeSuperClass(bean.getClass(), fieldList);
            if (!CollectionUtils.isEmpty(fieldList)) {
                for (Field field : fieldList) {
                    field.setAccessible(true);
                    if (field.get(bean) != null) {
                        continue;
                    }

//                    Autowired autowired = field.getAnnotation(Autowired.class);
//                    if (autowired != null) {
//                        String[] injectBeanNames = beanFactory.getBeanNamesForType(field.getType());
//                        if (injectBeanNames == null || injectBeanNames.length == 0) {
//                            throw new IllegalArgumentException("Can not find the corresponding " +
//                                    "type of autowired filed:[" + field.getName() + "] when inject bean:[" +
//                                    beanName + "]");
//                        } else if (injectBeanNames.length > 1) {
//                            throw new IllegalArgumentException("Find multiple matching " +
//                                    "classes of autowired filed:[" + field.getName() + "] when inject bean:[" +
//                                    beanName + "]");
//                        } else {
//                            Object injectBean = beanFactory.getBean(injectBeanNames[0]);
//                            field.set(bean, injectBean);
//                        }
//                    }

                    EzRemoting ezRemoting = field.getAnnotation(EzRemoting.class);
                    if (ezRemoting != null) {
                        Object injectBean = RemoteProxyFactory.create(field.getType(), ezRemoting.protocol());
                        field.set(bean, injectBean);
                    }
                }
            }
            return bean;
        } catch (IllegalAccessException ex) {
            log.error(ex.getMessage(), ex);
            throw new BeanInitializationException(ex.getMessage(), ex);
        }
    }

    /**
     * check whether EzDDD's create bean annotation exists.
     * @param bean
     * @return
     */
    private boolean hasEzAnnotation(Object bean) {
        if (ClassUtil.findAnnotation(bean.getClass(), EzComponent.class) != null ||
                AnnotationUtils.findAnnotation(bean.getClass(), EzAppService.class) != null ||
                AnnotationUtils.findAnnotation(bean.getClass(), EzDomainService.class) != null
                ) {
            return true;
        } else {
            return false;
        }
    }



}
