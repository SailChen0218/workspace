package com.ezddd.core.dispatcher;

import com.ezddd.core.appservice.AppService;
import com.ezddd.core.appservice.AppServiceDefinition;
import com.ezddd.core.appservice.AppServiceRegistry;
import com.ezddd.core.command.AbstractCommand;
import com.ezddd.core.command.Command;
import com.ezddd.core.response.AppResult;
import com.ezddd.core.utils.BeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AbstractDispatcher implements Dispatcher {
    private static final Logger log = LoggerFactory.getLogger(AbstractDispatcher.class);

    @Autowired
    protected AppServiceRegistry appServiceRegistry;

    @Override
    public AppResult doDispatch(Map<String, Object> parameters) {
        Assert.notNull(parameters.get("bizCode"), "The bizCode must not be null.");
        Assert.notNull(parameters.get("bizDetailCode"), "The bizDetailCode must not be null.");

        String bizCode = parameters.get("bizCode").toString();
        String bizDetailCode = parameters.get("bizDetailCode").toString();

        AppServiceDefinition appServiceDefinition = appServiceRegistry.findAppServiceDefinition(bizCode);
        Assert.notNull(appServiceDefinition, "Can't find the appservice.");

        AppService appService = appServiceDefinition.getAppService();
        Method method = appServiceDefinition.findBizDetailMethod(bizDetailCode);
        Assert.notNull(appServiceDefinition, "Can't find the method of bizDetailCode:" + bizDetailCode + ".");

        try {
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes == null) {
                return (AppResult)method.invoke(appService, new Object[]{});
            } else {
                if (parameterTypes.length != 1) {
                    throw new IllegalArgumentException("Only one command parameter is allowed.");
                } else {
                    Class<?> cmdClazz = parameterTypes[0];
                    AbstractCommand cmd = (AbstractCommand) BeanUtils.instantiateClass(cmdClazz);
                    parseCommand(cmd, parameters, parameterTypes[0]);
                    return (AppResult)method.invoke(appService, new Object[]{cmd});
                }
            }
        } catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
            return AppResult.valueOfError("001");
        } catch (InvocationTargetException e) {
            log.error(e.getMessage(), e);
            return AppResult.valueOfError("001");
        }
    }


    private void parseCommand(Command cmd, Map<String, Object> parameters, Class<?> clazz) throws IllegalAccessException {
        List<Field> fields = new ArrayList<>(16);
        BeanUtil.findFiledsIncludeSuperClass(clazz, fields);
        Field field = null;
        if (fields.size() != 0) {
            for (int i = 0; i < fields.size(); i++) {
                field = fields.get(i);
                if (parameters.get(field.getName()) != null) {
                    field.setAccessible(true);
                    field.set(cmd, parameters.get(field.getName()));
                }
            }
        }
    }


}