package com.ezddd.core.spring;

import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.annotation.EzRemoting;
import com.ezddd.core.registry.Registry;
import com.ezddd.core.remote.RemoteProxyFactory;
import com.ezddd.core.utils.BeanUtil;
import com.ezddd.core.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.CannotLoadBeanClassException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@EzComponent
public class EzBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    private static final Logger log = LoggerFactory.getLogger(EzBeanFactoryPostProcessor.class);
    private static final String area = System.getProperty("area");
    private DefaultListableBeanFactory beanFactory;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
        AnnotationTypeFilter[] annotationTypeFilterArray = EzBeanScannerRegistrar.getAnnotationTypeFilter();
        for (AnnotationTypeFilter annotationTypeFilter : annotationTypeFilterArray) {
            removeAreaUnmatchBeanDefinition(annotationTypeFilter.getAnnotationType());
        }

        for (AnnotationTypeFilter annotationTypeFilter : annotationTypeFilterArray) {
            removeLowPriorityBeanDefinition(annotationTypeFilter.getAnnotationType());
        }

        Map<String, Registry> registryMap = beanFactory.getBeansOfType(Registry.class);
        for (Registry registry: registryMap.values()) {
            registry.registry(beanFactory);
        }

        for (AnnotationTypeFilter annotationTypeFilter : EzAnnotationTypeFilter.annotationTypeOfNoInstantiation) {
            removeNoInstantiationBeanDefinition(annotationTypeFilter.getAnnotationType());
        }

        for (AnnotationTypeFilter annotationTypeFilter : annotationTypeFilterArray) {
            autowireInject(annotationTypeFilter.getAnnotationType());
        }
    }

    private <A extends Annotation> void removeAreaUnmatchBeanDefinition(Class<A> annotationClazz) throws BeansException {
        String beanName = null;
        GenericBeanDefinition beanDefinition = null;
        try {
            String[] beanNames = beanFactory.getBeanNamesForAnnotation(annotationClazz);
            if (beanNames != null && beanNames.length > 0) {
                for (int i = 0; i < beanNames.length; i++) {
                    beanName = beanNames[i];
                    beanDefinition = (GenericBeanDefinition) beanFactory.getBeanDefinition(beanName);
                    beanDefinition.resolveBeanClass(Thread.currentThread().getContextClassLoader());
                    A Annotation = beanDefinition.getBeanClass().getAnnotation(annotationClazz);
                    String beanArea = getAreaOfAnnotation(Annotation);
                    if (!"standard".equals(beanArea) && !beanArea.equals(area)) {
                        removeBeanDefineByName(beanName);
                    }
                }
            }
        } catch (ClassNotFoundException ex) {
            throw new CannotLoadBeanClassException(beanDefinition.getResourceDescription(),
                    beanName, beanDefinition.getBeanClassName() ,ex);
        }
    }

    private <A extends Annotation> void removeLowPriorityBeanDefinition(Class<A> annotationClazz) {
        String[] beanNames = beanFactory.getBeanNamesForAnnotation(annotationClazz);
        List<String> detectedBeanList = new ArrayList<>(16);
        for (String beanName : beanNames) {
            if (detectedBeanList.contains(beanName)) {
                continue;
            } else {
                detectedBeanList.add(beanName);
            }

            GenericBeanDefinition beanDefinition = (GenericBeanDefinition) beanFactory.getBeanDefinition(beanName);
            int beanPriority = getPriorityOfBean(beanDefinition, annotationClazz);
            A annotation = beanDefinition.getBeanClass().getAnnotation(annotationClazz);
            if (annotation == null) {
                return;
            }

            String[] subBeanNames = beanFactory.getBeanNamesForType(beanDefinition.getBeanClass());
            if (subBeanNames != null && subBeanNames.length > 1) {
                for (String subBeanName : subBeanNames) {
                    if (subBeanName.equals(beanName)) {
                        continue;
                    }
                    detectedBeanList.add(subBeanName);
                    GenericBeanDefinition subBeanDefinition =
                            (GenericBeanDefinition) beanFactory.getBeanDefinition(subBeanName);
                    int subBeanPriority = getPriorityOfBean(subBeanDefinition, annotationClazz);
                    if (beanPriority == subBeanPriority) {
                        throw new IllegalArgumentException("bean:" + beanDefinition.getBeanClassName() +
                                " should not have the same priority with bean:" + subBeanDefinition.getBeanClassName());
                    } else if (beanPriority < subBeanPriority) {
                        removeBeanDefineByName(beanName);
                        beanDefinition = subBeanDefinition;
                        beanPriority = subBeanPriority;
                    } else {
                        removeBeanDefineByName(subBeanName);
                    }
                }
            }
        }
    }

    private <A extends Annotation> int getPriorityOfBean(GenericBeanDefinition beanDefinition,
                                                         Class<A> annotationClass) {
        Assert.notNull(beanDefinition, "beanDefinition must not be null.");
        A annotation = beanDefinition.getClass().getAnnotation(annotationClass);
        if (annotation != null) {
            return getPriorityOfAnnotation(annotation);
        }
        return 0;
    }

    private int getPriorityOfAnnotation(Annotation annotation) {
        Map<String, Object> annotationAttributes = AnnotationUtils.getAnnotationAttributes(annotation);
        try {
            return Integer.parseInt(annotationAttributes.get("priority").toString());
        } catch (Exception ex) {
            return 0;
        }
    }

    private String getAreaOfAnnotation(Annotation annotation) {
        Map<String, Object> annotationAttributes = AnnotationUtils.getAnnotationAttributes(annotation);
        Object area = annotationAttributes.get("area");
        if (area != null) {
            return annotationAttributes.get("area").toString();
        } else {
            return "";
        }
    }

    private void removeBeanDefineByName(String beanName) throws NoSuchBeanDefinitionException {
        try {
            beanFactory.removeBeanDefinition(beanName);
        } catch (NoSuchBeanDefinitionException ex) {
            beanFactory.removeBeanDefinition(StringUtil.toLowerCaseFirstOne(beanName));
        }
    }

    private <A extends Annotation> void autowireInject(Class<A> annotationClazz) throws BeansException {
        String[] beanNames = beanFactory.getBeanNamesForAnnotation(annotationClazz);
        for (String beanName : beanNames) {
            try {
                Object bean = beanFactory.getBean(beanName);
                List<Field> fieldList = new ArrayList<>();
                BeanUtil.findFiledsIncludeSuperClass(bean.getClass(), fieldList);
                if (!CollectionUtils.isEmpty(fieldList)) {
                    for (Field field : fieldList) {
                        field.setAccessible(true);
                        if (field.get(bean) != null) {
                            continue;
                        }

                        Autowired autowired = field.getAnnotation(Autowired.class);
                        if (autowired != null) {
                            String[] injectBeanNames = beanFactory.getBeanNamesForType(field.getType());
                            if (injectBeanNames == null || injectBeanNames.length == 0) {
                                throw new IllegalArgumentException("Can not find the corresponding " +
                                        "type of autowired filed:[" + field.getName() + "] when inject bean:[" +
                                        beanName + "]");
                            } else if (injectBeanNames.length > 1) {
                                throw new IllegalArgumentException("Find multiple matching " +
                                        "classes of autowired filed:[" + field.getName() + "] when inject bean:[" +
                                        beanName + "]");
                            } else {
                                Object injectBean = beanFactory.getBean(injectBeanNames[0]);
                                field.set(bean, injectBean);
                            }
                        }

                        EzRemoting ezRemoting = field.getAnnotation(EzRemoting.class);
                        if (ezRemoting != null) {
                            Object injectBean = RemoteProxyFactory.create(field.getType(), ezRemoting.protocol());
                            field.set(bean, injectBean);
                        }
                    }
                }
            } catch (IllegalAccessException ex) {
                log.error(ex.getMessage(), ex);
                throw new BeanInitializationException(ex.getMessage(), ex);
            }
        }
    }

    private <A extends Annotation> void removeNoInstantiationBeanDefinition(Class<A> annotationClazz) {
        String[] beanNames = this.beanFactory.getBeanNamesForAnnotation(annotationClazz);
        if (beanNames != null && beanNames.length > 0) {
            for (int i = 0; i < beanNames.length; i++) {
                this.beanFactory.removeBeanDefinition(beanNames[i]);
            }
        }
    }
}
