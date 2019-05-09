package com.ezddd.core.command.impl;

import com.ezddd.core.annotation.EzAggregate;
import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.command.CommandDefinition;
import com.ezddd.core.command.CommandRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@EzComponent
public class CommandRegistryImpl implements CommandRegistry {
    private static final Logger log = LoggerFactory.getLogger(CommandRegistryImpl.class);
    // <CommandTypeName, CommandHandler>

    protected static Map<String, CommandDefinition> commandDefinitionHolder = new HashMap<>(16);
    protected static Map<String, BeanDefinition> aggregateBeanDefinitionHolder = new HashMap<>(16);

    @Override
    public void registry(BeanFactory beanFactory) {
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) beanFactory;
        String[] beanNames = defaultListableBeanFactory.getBeanNamesForAnnotation(EzAggregate.class);
        GenericBeanDefinition beanDefinition = null;
        if (beanNames != null && beanNames.length > 0) {
            for (int i = 0; i < beanNames.length; i++) {
                beanDefinition = (GenericBeanDefinition)defaultListableBeanFactory.getBeanDefinition(beanNames[i]);
                aggregateBeanDefinitionHolder.put(beanNames[i], beanDefinition);
                defaultListableBeanFactory.removeBeanDefinition(beanNames[i]);
                registerCommand(beanDefinition, beanFactory);
            }
        }
    }

    public void registerCommand(GenericBeanDefinition beanDefinition, BeanFactory beanFactory) {
        if (beanDefinition != null) {
            Map<String, CommandDefinition> commandDefinitionMap =
                    CommandDefinition.build(beanDefinition.getBeanClass(), beanFactory);
            if (commandDefinitionMap != null) {
                Set<String> keys = commandDefinitionHolder.keySet();
                for (String newKey : commandDefinitionMap.keySet()) {
                    if (keys.contains(newKey)) {
                        throw new IllegalArgumentException("command :[" + newKey + "] has already exist. aggregate:"
                                + commandDefinitionMap.get(newKey).getAggregateType());
                    } else {
                        log.info("command:[" + newKey + "] registration have successfully completed.");
                        commandDefinitionHolder.put(newKey, commandDefinitionMap.get(newKey));
                    }
                }
            }
        }
    }

    @Override
    public CommandDefinition findCommandDefinition(String commandName) {
        return commandDefinitionHolder.get(commandName);
    }
}
