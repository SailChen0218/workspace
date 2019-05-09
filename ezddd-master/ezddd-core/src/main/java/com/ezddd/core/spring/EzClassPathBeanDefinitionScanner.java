package com.ezddd.core.spring;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.HashSet;
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
            this.addIncludeFilter(annotationTypeFilter);
        }
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
//        Set<BeanDefinitionHolder> filteredBeanDefinitions = null;
//
//        if (beanDefinitions != null && beanDefinitions.size() != 0) {
//            filteredBeanDefinitions = new HashSet<>(16);
//            for(BeanDefinitionHolder beanDefinitionHolder: beanDefinitions) {
//                AnnotatedBeanDefinition annotatedBeanDefinition =
//                        (AnnotatedBeanDefinition)beanDefinitionHolder.getBeanDefinition();
//                if (hasAnnotations(annotatedBeanDefinition)) {
//                    filteredBeanDefinitions.add(beanDefinitionHolder);
//                }
//            }
//        }
        return beanDefinitions;
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

    private boolean hasAnnotations(AnnotatedBeanDefinition beanDefinition) {
        for (AnnotationTypeFilter annotationTypeFilter : annotationTypeFilter) {
            if (beanDefinition.getMetadata().hasAnnotation(annotationTypeFilter.getAnnotationType().getName())) {
                return true;
            }
        }
        return false;
    }
}
