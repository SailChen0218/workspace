package com.ezddd.core.command.impl;

import com.ezddd.core.annotation.EzAggregate;
import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.bean.EzBeanFactoryPostProcessor;
import com.ezddd.core.command.CommandDefinition;
import com.ezddd.core.command.CommandRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@EzComponent
public class CommandRegistryImpl implements CommandRegistry {
    private static final Logger log = LoggerFactory.getLogger(EzBeanFactoryPostProcessor.class);
    // <CommandTypeName, CommandHandler>

    protected static Map<String, CommandDefinition> commandDefinitionHolder = new HashMap<>(16);

    @Override
    public void registry(BeanFactory beanFactory) {
        ConfigurableListableBeanFactory configurableListableBeanFactory = (ConfigurableListableBeanFactory) beanFactory;
        String[] beanNames = configurableListableBeanFactory.getBeanNamesForAnnotation(EzAggregate.class);
        if (beanNames != null && beanNames.length > 0) {
            for (int i = 0; i < beanNames.length; i++) {
                Object bean = configurableListableBeanFactory.getBean(beanNames[i]);
                registerCommand(bean, beanFactory);
            }
        }
    }

    public void registerCommand(Object bean, BeanFactory beanFactory) {
        if (bean != null) {
            Map<String, CommandDefinition> commandDefinitionMap = CommandDefinition.build(bean.getClass(), beanFactory);
            if (commandDefinitionMap != null) {
                Set<String> keys = commandDefinitionMap.keySet();
                for (String newKey : commandDefinitionMap.keySet()) {
                    if (keys.contains(newKey)) {
                        throw new IllegalArgumentException("command :[" + newKey + "] has already exist.");
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
