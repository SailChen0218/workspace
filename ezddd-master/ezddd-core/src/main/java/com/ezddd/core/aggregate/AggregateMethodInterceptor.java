package com.ezddd.core.aggregate;

import com.ezddd.core.annotation.EzCommandHandler;
import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.command.CommandDefinition;
import com.ezddd.core.command.CommandRegistry;
import com.ezddd.core.command.CommandType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

@EzComponent
public class AggregateMethodInterceptor implements MethodInterceptor {

    @Autowired
    CommandRegistry commandRegistry;

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        Object result = methodProxy.invokeSuper(obj, args);
        EzCommandHandler ezCommandHandler = method.getAnnotation(EzCommandHandler.class);
        if (ezCommandHandler != null) {
            Class<?>[] parameterTypes = method.getParameterTypes();
            CommandDefinition commandDefinition =
                    commandRegistry.findCommandDefinition(parameterTypes[0].getSimpleName());
            int commandType = commandDefinition.getCommandType();
            if (commandType == CommandType.CREATE ||
                    commandType == CommandType.UPDATE ||
                    commandType == CommandType.DELETE) {

            }
        }
        return null;
    }
}
