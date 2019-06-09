package com.ezddd.core.dispatcher;

import com.ezddd.core.appservice.AppServiceDefinition;
import com.ezddd.core.appservice.AppServiceRegistry;
import com.ezddd.core.appservice.MethodDefiniton;
import com.ezddd.core.command.AbstractCommand;
import com.ezddd.core.command.Command;
import com.ezddd.core.command.query.AbstractQueryParam;
import com.ezddd.core.command.query.QueryParam;
import com.ezddd.core.command.query.QueryParameter;
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
import java.lang.reflect.Parameter;
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
    public AppResult doDispatch(Map<String, Object> requestParameters) {
        Assert.notNull(requestParameters.get(BIZ_CODE), "The bizCode must not be null.");
        Assert.notNull(requestParameters.get(BIZ_DETAIL_CODE), "The bizDetailCode must not be null.");

        String bizCode = requestParameters.get(BIZ_CODE).toString();
        String bizDetailCode = requestParameters.get(BIZ_DETAIL_CODE).toString();

        AppServiceDefinition appServiceDefinition = appServiceRegistry.findAppServiceDefinition(bizCode);
        Assert.notNull(appServiceDefinition, "Can't find the appservice.");

        MethodDefiniton methodDefiniton = appServiceDefinition.findMethodDefinition(bizDetailCode);
        Assert.notNull(appServiceDefinition, "Can't find the MethodDefiniton of bizDetailCode:"
                + bizDetailCode + ".");

        try {
            Method method = methodDefiniton.getMethod();
            if (methodDefiniton.getMethodType() == MethodDefiniton.COMMAND) {
                return invokeCommand(method, appServiceDefinition, requestParameters);
            } else if (methodDefiniton.getMethodType() == MethodDefiniton.QUERY) {
                return invokeQuery(method, appServiceDefinition, requestParameters);
            } else {
                return AppResult.valueOfError("E9999");
            }
        } catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
            return AppResult.valueOfError(e.getMessage(), "E9999");
        } catch (InvocationTargetException e) {
            log.error(e.getMessage(), e);
            return AppResult.valueOfError(e.getCause().getMessage(), "E9999");
        }
    }

    private AppResult invokeCommand(Method method, AppServiceDefinition appServiceDefinition,
                                    Map<String, Object> requestParameters)
            throws InvocationTargetException, IllegalAccessException {
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes == null) {
            return (AppResult) method.invoke(appServiceDefinition.getAppService(), new Object[]{});
        } else {
            if (parameterTypes.length != 1) {
                throw new IllegalArgumentException("Only one command parameter is allowed. appService:"
                        + appServiceDefinition.getAppServiceType().getName() + ", method:" + method.getName());
            } else {
                Class<?> cmdClazz = parameterTypes[0];
                AbstractCommand cmd = (AbstractCommand) BeanUtils.instantiateClass(cmdClazz);
                parseCommand(cmd, requestParameters, parameterTypes[0]);
                return (AppResult) method.invoke(appServiceDefinition.getAppService(), new Object[]{cmd});
            }
        }
    }

    private void parseCommand(Command cmd, Map<String, Object> requestParameters, Class<?> clazz) throws IllegalAccessException {
        List<Field> fields = new ArrayList<>(16);
        ClassUtil.findFiledsIncludeSuperClass(clazz, fields);
        Field field = null;
        if (fields.size() != 0) {
            Map<String, Object> dataMap = (Map<String, Object>) requestParameters.get("data");
            for (int i = 0; i < fields.size(); i++) {
                field = fields.get(i);
                if (BIZ_CODE.equals(field.getName())) {
                    Object bizCode = requestParameters.get(BIZ_CODE);
                    field.setAccessible(true);
                    field.set(cmd, bizCode);
                    continue;
                }

                if (BIZ_DETAIL_CODE.equals(field.getName())) {
                    Object bizDetailCode = requestParameters.get(BIZ_DETAIL_CODE);
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

    private AppResult invokeQuery(Method method, AppServiceDefinition appServiceDefinition,
                                    Map<String, Object> requestParameters)
            throws InvocationTargetException, IllegalAccessException {
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes == null || parameterTypes.length == 0) {
            return (AppResult) method.invoke(appServiceDefinition.getAppService(), new Object[]{});
        } else {
            Parameter[] parameters = method.getParameters();
            Map<String, Object> dataMap = (Map<String, Object>) requestParameters.get("data");
            Object[] methodParam = new Object[parameters.length];
            if (parameters.length == 1 && QueryParam.class.isAssignableFrom(parameters[0].getType())) {
                Class<?> queryParamType = parameters[0].getType();
                QueryParam queryParam = (QueryParam) BeanUtils.instantiateClass(queryParamType);
                parseQuery(queryParam, dataMap, queryParamType);
                methodParam[0] = queryParam;
            } else {
                for (int i = 0; i < parameters.length; i++) {
                    Class<?> queryParamType = parameters[i].getType();
                    if (!isPrimitive(queryParamType)) {
                        throw new IllegalArgumentException("Only one parameter is allowed when use non-primitive " +
                                "type parameter. appService:" + appServiceDefinition.getAppServiceType().getName() +
                                ", method:" + method.getName());
                    } else {
                        QueryParameter queryParameter = parameters[i].getAnnotation(QueryParameter.class);
                        if (queryParameter == null) {
                            throw new IllegalArgumentException("Query method parameter should annotated by QueryParameter. " +
                                    "appService:" + appServiceDefinition.getAppServiceType().getName() +
                                    ", method:" + method.getName());
                        } else {
                            String parameterName = queryParameter.value();
                            methodParam[i] = dataMap.get(parameterName);
                        }
                    }
                }
            }
            return (AppResult) method.invoke(appServiceDefinition.getAppService(), methodParam);
        }
    }

    private boolean isPrimitive(Class<?> type) {
        if (type.isPrimitive() || String.class.equals(type)) {
            return true;
        } else {
            return false;
        }
    }

    private void parseQuery(QueryParam queryParam, Map<String, Object> dataMap, Class<?> clazz) throws IllegalAccessException {
        List<Field> fields = new ArrayList<>(16);
        ClassUtil.findFiledsIncludeSuperClass(clazz, fields);
        Field field = null;
        if (fields.size() != 0) {
            for (int i = 0; i < fields.size(); i++) {
                field = fields.get(i);
                Object value = dataMap.get(field.getName());
                if (value != null) {
                    field.setAccessible(true);
                    field.set(queryParam, value);
                }
            }
        }
    }
}