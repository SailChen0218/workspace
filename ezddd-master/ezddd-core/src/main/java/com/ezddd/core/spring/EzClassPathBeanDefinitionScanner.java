package com.ezddd.core.spring;

import com.ezddd.core.annotation.EzAppService;
import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.annotation.EzDomainService;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;

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
        this.beanFactory = (DefaultListableBeanFactory) registry;
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
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) registry;
        GenericBeanDefinition beanDefinition;
        for (BeanDefinitionHolder holder : beanDefinitionHolders) {
            beanDefinition = (GenericBeanDefinition) holder.getBeanDefinition();
            String beanClassName = beanDefinition.getBeanClassName();
            Class<?> beanClass = ClassUtils.resolveClassName(beanClassName, beanFactory.getBeanClassLoader());
            if (hasEzAnnotation(beanClass)) {
                beanDefinition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_NAME);
                beanDefinition.setAutowireCandidate(true);
//                beanDefinition.setDependencyCheck(AbstractBeanDefinition.DEPENDENCY_CHECK_ALL);
            }
        }
    }

    /**
     * check whether EzDDD's create bean annotation exists.
     *
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
