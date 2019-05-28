package com.ezddd.core.spring;

import com.ezddd.core.annotation.EzAppService;
import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.annotation.EzDomainService;
import com.ezddd.core.utils.ClassUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.*;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EzClassPathBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {
//    private AnnotationTypeFilter[] annotationTypeFilters;
    private BeanDefinitionRegistry registry;
    private DefaultListableBeanFactory beanFactory;

    public EzClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry,
                                            AnnotationTypeFilter[] annotationTypeFilters) {
        super(registry, true);
//        this.annotationTypeFilters = annotationTypeFilters;
        for (AnnotationTypeFilter annotationTypeFilter : annotationTypeFilters) {
            this.addIncludeFilter(annotationTypeFilter);
        }
        this.registry = registry;
        this.beanFactory = (DefaultListableBeanFactory)registry;
        this.registerDefaultFilters();

    }

    /**
     * Calls the parent search that will search and register all the candidates.
     * Then the registered objects are post processed to set them as
     * MapperFactoryBeans
     */
    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);

//        AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(registry);
//        if (beanDefinitionHolders != null && beanDefinitionHolders.size() != 0) {
//            for(BeanDefinitionHolder beanDefinitionHolder: beanDefinitionHolders) {
////                GenericBeanDefinition genericBeanDefinition =
////                        (GenericBeanDefinition)beanDefinitionHolder.getBeanDefinition();
////                genericBeanDefinition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
////                genericBeanDefinition.setDependencyCheck(AbstractBeanDefinition.DEPENDENCY_CHECK_OBJECTS);
////                ClassLoader classLoader = ClassUtils.getDefaultClassLoader();
//////                resolveClassName
////                String beanClassName = beanDefinitionHolder.getBeanDefinition().getBeanClassName();
////                Class<?> beanClass = ClassUtils.resolveClassName(beanClassName, beanFactory.getBeanClassLoader());
////                reader.register(beanClass);
////
////                BeanDefinitionReaderUtils.registerBeanDefinition(beanDefinitionHolder, registry);
//
//                GenericBeanDefinition genericBeanDefinition =
//                    (GenericBeanDefinition)beanDefinitionHolder.getBeanDefinition();
//
//                beanDefinitionHolder.getBeanDefinition().setAutowireCandidate(true);
//                beanDefinitionHolder.getBeanDefinition().
//            }
//        }
        processBeanDefinitions(beanDefinitionHolders);
        return beanDefinitionHolders;
    }

//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
//        return super.isCandidateComponent(beanDefinition) & hasAnnotations(beanDefinition);
//    }

//    @Override
//    protected boolean checkCandidate(String beanName, BeanDefinition beanDefinition) throws IllegalStateException {
//        boolean isCandidate = super.checkCandidate(beanName, beanDefinition);
//        if (isCandidate) {
//            AnnotatedBeanDefinition annotatedBeanDefinition = (AnnotatedBeanDefinition)beanDefinition;
//            isCandidate = hasAnnotations(annotatedBeanDefinition);
//        }
//        return isCandidate;
//    }

//    private boolean hasAnnotations(AnnotatedBeanDefinition beanDefinition) {
//        for (AnnotationTypeFilter annotationTypeFilter : annotationTypeFilters) {
//            if (beanDefinition.getMetadata().hasAnnotation(annotationTypeFilter.getAnnotationType().getName())) {
//                return true;
//            }
//        }
//        return false;
//    }

    private void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitionHolders) {
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory)registry;
        GenericBeanDefinition beanDefinition;
        for (BeanDefinitionHolder holder : beanDefinitionHolders) {
            beanDefinition = (GenericBeanDefinition) holder.getBeanDefinition();
            String beanClassName = beanDefinition.getBeanClassName();
            Class<?> beanClass = ClassUtils.resolveClassName(beanClassName, beanFactory.getBeanClassLoader());
            if (hasEzAnnotation(beanClass)) {
//                List<Field> fieldList = new ArrayList<>();
//                ClassUtil.findFiledsIncludeSuperClass(beanClass, fieldList);
//                if (!CollectionUtils.isEmpty(fieldList)) {
//                    for (Field field : fieldList) {
//                        Autowired autowired = field.getAnnotation(Autowired.class);
//                        if (autowired != null) {
//                            String[] injectBeanNames = beanFactory.getBeanNamesForType(field.getType());
//                            if (injectBeanNames == null || injectBeanNames.length == 0) {
//                                throw new IllegalArgumentException("Can not find the corresponding " +
//                                        "type of autowired filed:[" + field.getName() + "] when inject bean:[" +
//                                        beanClassName + "]");
//                            } else if (injectBeanNames.length > 1) {
//                                throw new IllegalArgumentException("Find multiple matching " +
//                                        "classes of autowired filed:[" + field.getName() + "] when inject bean:[" +
//                                        beanClassName + "]");
//                            } else {
//                                Object injectBean = beanFactory.getBean(injectBeanNames[0]);
//                                field.set(bean, injectBean);
//                            }
//                        }
//
//                        EzRemoting ezRemoting = field.getAnnotation(EzRemoting.class);
//                        if (ezRemoting != null) {
//                            Object injectBean = RemoteProxyFactory.create(field.getType(), ezRemoting.protocol());
//                            field.set(bean, injectBean);
//                        }
//                    }
                    beanDefinition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
                    beanDefinition.setAutowireCandidate(true);
                    beanDefinition.setDependencyCheck(AbstractBeanDefinition.DEPENDENCY_CHECK_ALL);
                }
            }

//
//            PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(definition.getBeanClass());
//            if (propertyDescriptors != null) {
//                for (int i = 0; i < propertyDescriptors.length; i++) {
//                    propertyDescriptors[i].get
//                    Class<?> propertyType = propertyDescriptors[i].getPropertyType();
//
//                    if (propertyType.isSynthetic()) {
//
//                    }
//                }
//            }
//
//            // the mapper interface is the original class of the bean
//            // but, the actual class of the bean is MapperFactoryBean
//            definition.getConstructorArgumentValues().addGenericArgumentValue(definition.getBeanClassName()); // issue #59
//            definition.setBeanClass(this.mapperFactoryBean.getClass());
//
//            PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(sourceClazz, targetPd.getName());
//
//            definition.resolveBeanClass()
//            definition.getPropertyValues().add("addToConfig", this.addToConfig);
//
//            boolean explicitFactoryUsed = false;
//            if (StringUtils.hasText(this.sqlSessionFactoryBeanName)) {
//                definition.getPropertyValues().add("sqlSessionFactory", new RuntimeBeanReference(this.sqlSessionFactoryBeanName));
//                explicitFactoryUsed = true;
//            } else if (this.sqlSessionFactory != null) {
//                definition.getPropertyValues().add("sqlSessionFactory", this.sqlSessionFactory);
//                explicitFactoryUsed = true;
//            }
//
//            if (StringUtils.hasText(this.sqlSessionTemplateBeanName)) {
//                if (explicitFactoryUsed) {
//                    logger.warn("Cannot use both: sqlSessionTemplate and sqlSessionFactory together. sqlSessionFactory is ignored.");
//                }
//                definition.getPropertyValues().add("sqlSessionTemplate", new RuntimeBeanReference(this.sqlSessionTemplateBeanName));
//                explicitFactoryUsed = true;
//            } else if (this.sqlSessionTemplate != null) {
//                if (explicitFactoryUsed) {
//                    logger.warn("Cannot use both: sqlSessionTemplate and sqlSessionFactory together. sqlSessionFactory is ignored.");
//                }
//                definition.getPropertyValues().add("sqlSessionTemplate", this.sqlSessionTemplate);
//                explicitFactoryUsed = true;
//            }
//
//            if (!explicitFactoryUsed) {
//                if (logger.isDebugEnabled()) {
//                    logger.debug("Enabling autowire by type for MapperFactoryBean with name '" + holder.getBeanName() + "'.");
//                }
//                definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
//            }
    }

    /**
     * check whether EzDDD's create bean annotation exists.
     * @param beanClass
     * @return
     */
    private boolean hasEzAnnotation(Class<?> beanClass) {
        if (AnnotationUtils.findAnnotation(beanClass, EzComponent.class) != null ||
                AnnotationUtils.findAnnotation(beanClass, EzAppService.class) != null ||
                AnnotationUtils.findAnnotation(beanClass, EzDomainService.class) != null
                ) {
            return true;
        } else {
            return false;
        }
    }
}
