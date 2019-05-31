package com.ezddd.core.repository.impl;

import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.repository.Repository;
import com.ezddd.core.repository.RepositoryRegistry;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;

import java.lang.reflect.ParameterizedType;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@EzComponent
public class RepositoryRegistryImpl implements RepositoryRegistry {
    private static Map<String, RepositoryDefinition> repositoryDefinitionHolder = new ConcurrentHashMap<>();
    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public Repository findRepository(Class<?> aggregateType) {
        String aggregateName = aggregateType.getName();
        if (repositoryDefinitionHolder.containsKey(aggregateName)) {
            RepositoryDefinition repositoryDefinition = repositoryDefinitionHolder.get(aggregateName);
            if (repositoryDefinition.getRepository() == null) {
                repositoryDefinition.repository =
                        (Repository) this.beanFactory.getBean(repositoryDefinition.getRepositoryType());
            }
            return repositoryDefinition.repository;
        } else {
            throw new IllegalArgumentException("Repository not found, aggregateType:[" + aggregateName + "]. ");
        }
    }

    @Override
    public void registry(BeanFactory beanFactory) {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
        String[] beanNames = this.beanFactory.getBeanNamesForType(Repository.class);
        if (beanNames != null && beanNames.length > 0) {
            GenericBeanDefinition beanDefinition = null;
            for (int i = 0; i < beanNames.length; i++) {
                beanDefinition = (GenericBeanDefinition) this.beanFactory.getBeanDefinition(beanNames[i]);
                RepositoryDefinition repositoryDefinition = new RepositoryDefinition(beanDefinition.getBeanClass());
                repositoryDefinitionHolder.put(getAggregateType(beanDefinition.getBeanClass()),
                        repositoryDefinition);
            }
        }
    }

    private String getAggregateType(Class<?> repositoryType) {
        try {
            return ((ParameterizedType) repositoryType.getGenericSuperclass())
                    .getActualTypeArguments()[0].getTypeName();
        } catch (Exception ex) {
            throw new IllegalArgumentException("The aggregate type of repository is invalid. repositoryType:"
                    + repositoryType.getName());
        }
    }

    private class RepositoryDefinition {
        private Class<?> repositoryType;
        private Repository repository;

        public RepositoryDefinition(Class<?> repositoryType) {
            this.repositoryType = repositoryType;
        }

        public Class<?> getRepositoryType() {
            return repositoryType;
        }

        public void setRepositoryType(Class<?> repositoryType) {
            this.repositoryType = repositoryType;
        }

        public Repository getRepository() {
            return repository;
        }

        public void setRepository(Repository repository) {
            this.repository = repository;
        }
    }
}
