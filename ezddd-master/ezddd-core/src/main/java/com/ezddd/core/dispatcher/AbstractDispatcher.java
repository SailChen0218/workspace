package com.ezddd.core.dispatcher;

import com.ezddd.core.appservice.AppService;
import com.ezddd.core.appservice.AppServiceDefinition;
import com.ezddd.core.appservice.AppServiceRegistry;
import com.ezddd.core.command.AbstractCommand;
import com.ezddd.core.command.Command;
import com.ezddd.core.response.AppResult;
import com.ezddd.core.utils.ClassUtil;
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

    private static final String BIZ_CODE = "bizCode";
    private static final String BIZ_DETAIL_CODE = "bizDetailCode";

    @Autowired
    protected AppServiceRegistry appServiceRegistry;

    @Override
    public AppResult doDispatch(Map<String, Object> parameters) {
        Assert.notNull(parameters.get(BIZ_CODE), "The bizCode must not be null.");
        Assert.notNull(parameters.get(BIZ_DETAIL_CODE), "The bizDetailCode must not be null.");

        String bizCode = parameters.get(BIZ_CODE).toString();
        String bizDetailCode = parameters.get(BIZ_DETAIL_CODE).toString();

        AppServiceDefinition appServiceDefinition = appServiceRegistry.findAppServiceDefinition(bizCode);
        Assert.notNull(appServiceDefinition, "Can't find the appservice.");

        AppService appService = appServiceDefinition.getAppService();
        Method method = appServiceDefinition.findBizDetailMethod(bizDetailCode);
        Assert.notNull(appServiceDefinition, "Can't find the method of bizDetailCode:" + bizDetailCode + ".");

        try {
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes == null) {
                return (AppResult) method.invoke(appService, new Object[]{});
            } else {
                if (parameterTypes.length != 1) {
                    throw new IllegalArgumentException("Only one command parameter is allowed.");
                } else {
                    Class<?> cmdClazz = parameterTypes[0];
                    AbstractCommand cmd = (AbstractCommand) BeanUtils.instantiateClass(cmdClazz);
                    parseCommand(cmd, parameters, parameterTypes[0]);
                    return (AppResult) method.invoke(appService, new Object[]{cmd});
                }
            }
        } catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
            return AppResult.valueOfError(e.getMessage(), "E9999");
        } catch (InvocationTargetException e) {
            log.error(e.getMessage(), e);
            return AppResult.valueOfError(e.getCause().getMessage(), "E9999");
        }
    }

    private void parseCommand(Command cmd, Map<String, Object> parameters, Class<?> clazz) throws IllegalAccessException {
        List<Field> fields = new ArrayList<>(16);
        ClassUtil.findFiledsIncludeSuperClass(clazz, fields);
        Field field = null;
        if (fields.size() != 0) {
            Map<String, Object> dataMap = (Map<String, Object>) parameters.get("data");
            for (int i = 0; i < fields.size(); i++) {
                field = fields.get(i);
                if (BIZ_CODE.equals(field.getName())) {
                    Object bizCode = parameters.get(BIZ_CODE);
                    field.setAccessible(true);
                    field.set(cmd, bizCode);
                    continue;
                }

                if (BIZ_DETAIL_CODE.equals(field.getName())) {
                    Object bizDetailCode = parameters.get(BIZ_DETAIL_CODE);
                    field.setAccessible(true);
                    field.set(cmd, bizDetailCode);
                    continue;
                }

                Object value = dataMap.get(field.getName());
                if (value != null) {
                    field.setAccessible(true);
                    field.set(cmd, value);
                }
            }
        }
    }
}