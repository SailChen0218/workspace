package com.ezddd.core.bean;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class EzBeanScannerRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {

    private ResourceLoader resourceLoader;

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        EzClassPathBeanDefinitionScanner scanner = new EzClassPathBeanDefinitionScanner(registry);
        // this check is needed in Spring 3.1
        if (resourceLoader != null) {
            scanner.setResourceLoader(resourceLoader);
        }

        List<String> basePackages = new ArrayList<String>();

        boolean isApplication = importingClassMetadata.hasAnnotation(EnableEzdddApplication.class.getName());
        if (isApplication) {
            basePackages.add("com.ezddd.core.bean");
            basePackages.add("com.ezddd.core.registry");
            basePackages.add("com.ezddd.core.remote.consumer");
            basePackages.add("com.ezddd.core.appservice");
            basePackages.add("com.ezddd.core.dispatcher");

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
            basePackages.add("com.ezddd.common.aggregate");
            basePackages.add("com.ezddd.core.bean");
            basePackages.add("com.ezddd.core.command");
            basePackages.add("com.ezddd.core.event");
            basePackages.add("com.ezddd.core.registry");
            basePackages.add("com.ezddd.core.remote.provider");
            basePackages.add("com.ezddd.core.repository");
//        basePackages.add("com.ezddd.common.reponse");
//        basePackages.add("com.ezddd.common.tunnel");
//        basePackages.add("com.ezddd.common.utils");
            basePackages.add("com.ezddd.core.appservice");
            basePackages.add("com.ezddd.core.dispatcher");

            AnnotationAttributes annoAttrs = AnnotationAttributes.fromMap(
                    importingClassMetadata.getAnnotationAttributes(EnableEzdddDomain.class.getName()));
            for (String pkg : annoAttrs.getStringArray("basePackages")) {
                if (StringUtils.hasText(pkg)) {
                    basePackages.add(pkg);
                }
            }
        }

        scanner.doScan(StringUtils.toStringArray(basePackages));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

}
