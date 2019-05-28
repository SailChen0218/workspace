package com.ezddd.core.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class EzBeanScannerRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {
    private static final Logger log = LoggerFactory.getLogger(EzBeanFactoryPostProcessor.class);
    private ResourceLoader resourceLoader;

    private static AnnotationTypeFilter[] annotationTypeFilter;

    public static AnnotationTypeFilter[] getAnnotationTypeFilter() {
        return annotationTypeFilter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        List<String> basePackages = new ArrayList<String>();
        boolean isApplication = importingClassMetadata.hasAnnotation(EnableEzdddApplication.class.getName());
        if (isApplication) {
            EzBeanScannerRegistrar.annotationTypeFilter = EzAnnotationTypeFilter.annotationTypeFilterForApplication;
            basePackages.add("com.ezddd.core.appservice");
            basePackages.add("com.ezddd.core.dispatcher");
            basePackages.add("com.ezddd.core.spring");
            AnnotationAttributes annoAttrs = AnnotationAttributes.fromMap(
                    importingClassMetadata.getAnnotationAttributes(EnableEzdddApplication.class.getName()));
            for (String pkg : annoAttrs.getStringArray("basePackages")) {
                if (StringUtils.hasText(pkg)) {
                    basePackages.add(pkg);
                }
            }
        }

        boolean isDomain = importingClassMetadata.hasAnnotation(EnableEzdddDomain.class.getName());
        if (isDomain) {
            EzBeanScannerRegistrar.annotationTypeFilter = EzAnnotationTypeFilter.annotationTypeFilterForDomain;
            basePackages.add("com.ezddd.core.aggregate");
            basePackages.add("com.ezddd.core.command");
            basePackages.add("com.ezddd.core.event");
            basePackages.add("com.ezddd.core.registry");
            basePackages.add("com.ezddd.core.remote.protocol");
            basePackages.add("com.ezddd.core.remote.receiver");
            basePackages.add("com.ezddd.core.repository");
            basePackages.add("com.ezddd.core.service");
            basePackages.add("com.ezddd.core.spring");
            basePackages.add("com.ezddd.extension");
            AnnotationAttributes annoAttrs = AnnotationAttributes.fromMap(
                    importingClassMetadata.getAnnotationAttributes(EnableEzdddDomain.class.getName()));
            for (String pkg : annoAttrs.getStringArray("basePackages")) {
                if (StringUtils.hasText(pkg)) {
                    basePackages.add(pkg);
                }
            }
        }

        doScann(registry, basePackages, annotationTypeFilter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /**
     * do components scan
     * @param registry
     * @param basePackages
     */
    private void doScann(BeanDefinitionRegistry registry, List<String> basePackages, AnnotationTypeFilter[] annotationTypeFilter) {
        EzClassPathBeanDefinitionScanner scanner = new EzClassPathBeanDefinitionScanner(registry, annotationTypeFilter);
        // this check is needed in Spring 3.1
        if (resourceLoader != null) {
            scanner.setResourceLoader(resourceLoader);
        }
        scanner.doScan(StringUtils.toStringArray(basePackages));

//        AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(registry);

//        AnnotationTypeFilter[] annotationTypeFilterArray = EzBeanScannerRegistrar.getAnnotationTypeFilter();
//        for (AnnotationTypeFilter annotationTypeFilter : annotationTypeFilterArray) {
//            log.info(registry.getClass().getSimpleName() + " will register annotated classes : "
//                    + annotationTypeFilter.getAnnotationType() + " .");
//            reader.registerBean(annotationTypeFilter.getAnnotationType());
//        }
    }


}
