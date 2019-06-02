package com.ezddd.core.spring;

import com.ezddd.core.annotation.EzAppService;
import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.annotation.EzDomainService;
import com.ezddd.core.annotation.EzRemoting;
import com.ezddd.core.remote.RemoteProxyFactory;
import com.ezddd.core.repository.AbstractRepository;
import com.ezddd.core.repository.Repository;
import com.ezddd.core.utils.ClassUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@EzComponent
public class EzBeanInstantiationAware implements BeanPostProcessor, ApplicationContextAware {
    private static final Logger log = LoggerFactory.getLogger(EzBeanInstantiationAware.class);
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (!hasEzAnnotation(bean)) {
            return bean;
        }

        if (Repository.class.isAssignableFrom(bean.getClass())) {
            this.populateRepository(bean);
        }

        return populateAutowiredProperty(bean, beanName);
    }

    private Object populateAutowiredProperty(Object bean, String beanName) {
        try {


            List<Field> fieldList = new ArrayList<>();
            ClassUtil.findFiledsIncludeSuperClass(bean.getClass(), fieldList);
            if (!CollectionUtils.isEmpty(fieldList)) {
                for (Field field : fieldList) {
                    field.setAccessible(true);
                    if (field.get(bean) != null) {
                        continue;
                    }

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
                ClassUtil.findAnnotation(bean.getClass(), EzAppService.class) != null ||
                ClassUtil.findAnnotation(bean.getClass(), EzDomainService.class) != null
                ) {
            return true;
        } else {
            return false;
        }
    }

    private void populateRepository(Object bean) {
        Class<?> repositoryType = bean.getClass();
        Type genericSuperclass = repositoryType.getGenericSuperclass();
        Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
        String aggrootTypeName = actualTypeArguments[0].getTypeName();
        Class<?> aggrootType = ClassUtil.forName(aggrootTypeName);
        ((AbstractRepository) bean).setAggregateType(aggrootType);
    }

}
