package com.ezddd.core.command.impl;

import com.ezddd.core.annotation.EzCommand;
import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.command.CommandBus;
import com.ezddd.core.command.CommandBusRegistry;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;

import java.util.HashMap;
import java.util.Map;

@EzComponent
public class CommandBusRegistryImpl implements CommandBusRegistry {

    /**
     * Map of *CommandClassName, CommandBus*
     */
    protected static Map<String, CommandBus> commandBusHolder = new HashMap<>(16);

    @Override
    public CommandBus findCommandBus(String commandName) {
        return commandBusHolder.get(commandName);
    }

    @Override
    public void registry(BeanFactory beanFactory) {
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) beanFactory;
        String[] beanNames = defaultListableBeanFactory.getBeanNamesForAnnotation(EzCommand.class);
        GenericBeanDefinition beanDefinition = null;
        if (beanNames != null && beanNames.length > 0) {
            for (int i = 0; i < beanNames.length; i++) {
                beanDefinition = (GenericBeanDefinition)defaultListableBeanFactory.getBeanDefinition(beanNames[i]);
                EzCommand ezCommand = beanDefinition.getBeanClass().getAnnotation(EzCommand.class);
                CommandBus commandBus = beanFactory.getBean(ezCommand.commandBus(), CommandBus.class);
                commandBusHolder.put(beanDefinition.getBeanClassName(), commandBus);
                defaultListableBeanFactory.removeBeanDefinition(beanNames[i]);
            }
        }
    }
}
