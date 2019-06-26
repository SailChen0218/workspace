package com.ezshop.desensitize;

import com.alibaba.fastjson.JSON;
import com.ezshop.desensitize.util.ReflectionUtils;
import com.ezshop.test.ResultDto;
import com.ezshop.test.ResultVo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
public class DesensitizeAspect {
    /**
     * map of [key:signature, value:method]
     */
    private static final Map<String, Method> methodOfJoinPointHolder = new ConcurrentHashMap<>(8);

    @Autowired
    DesensitizeProcessor desensitizeProcessor;

    @Autowired
    ValidateProcessor validateProcessor;

    @Pointcut("@annotation(com.ezshop.desensitize.Desensitized)")
    public void desensitized() {
    }

    @Around("desensitized()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 方法执行前进行入参验证
        Object result = this.validateJoinPoit(joinPoint);
        if (result != null) {
            return result;
        }

        // 执行方法
        Object object = joinPoint.proceed();

        // 获取渠道编号 //TODO
        String qdbh = "";

        // 获取交易服务编号 //TODO
        String jyfwbh = "";

        // 对返回值进行脱敏处理
        desensitize(object, qdbh, jyfwbh);

        System.out.println(JSON.toJSONString(object));
        return object;
    }

    /**
     * 校验方法入参
     * @param joinPoint
     * @return
     * @throws ValidateFailedException
     */
    private Object validateJoinPoit(JoinPoint joinPoint) throws ValidateFailedException {
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            Object target = joinPoint.getTarget();
            String methodName = joinPoint.getSignature().getName();
            String signature = joinPoint.getSignature().toString();
            Method joinPointMethod = null;
            if (methodOfJoinPointHolder.containsKey(signature)) {
                joinPointMethod = methodOfJoinPointHolder.get(signature);
            } else {
                joinPointMethod = findJoinPointMethod(target, methodName, args);
                methodOfJoinPointHolder.put(signature, joinPointMethod);
            }

            Set<ConstraintViolation<Object>> constraintViolationSet =
                    validateProcessor.validateParameters(target, joinPointMethod, args);
            if (constraintViolationSet.size() != 0) {
                Map<String, String> errors = new HashMap<>(constraintViolationSet.size());
                for (ConstraintViolation<Object> constraintViolation : constraintViolationSet) {
                    errors.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
                }
                Class<?> methodReturnType = joinPointMethod.getReturnType();
                if (ResultDto.class.isAssignableFrom(methodReturnType)) {
                    ResultDto resultDto = ResultDto.valueOfError("请求参数验证失败。");
                    resultDto.getResultMap().putAll(errors);
                    return resultDto;
                } else if (ResultVo.class.isAssignableFrom(methodReturnType)) {
                    ResultVo resultVo = ResultVo.valueOfError("请求参数验证失败。");
                    resultVo.getResultMap().putAll(errors);
                    return resultVo;
                } else {
                    throw new ValidateFailedException(errors, "请求参数验证失败。");
                }
            }
        }
        return null;
    }

    /**
     * 根据方法名称等信息获取连接点Method对象
     * @param target
     * @param methodName
     * @param args
     * @return
     */
    private Method findJoinPointMethod(Object target, String methodName, Object[] args) {
        Class<?> targetType = target.getClass();
        if (args != null) {
            List<Class<?>> argsTypeList = new ArrayList<>(args.length);
            for (int i = 0; i < args.length; i++) {
                argsTypeList.add(args[i].getClass());
            }
            Class<?>[] argsTypes = argsTypeList.toArray(new Class<?>[argsTypeList.size()]);
            return ReflectionUtils.findMethod(targetType, methodName, argsTypes);
        } else {
            return ReflectionUtils.findMethod(targetType, methodName);
        }
    }

    /**
     * 返回值脱敏
     *
     * @param target 脱敏对象
     * @param qdbh   渠道编号
     * @param jyfwbh 交易服务编号
     * @throws DesensitizeFailedException
     */
    private void desensitize(Object target, String qdbh, String jyfwbh) throws DesensitizeFailedException {
        if (target == null) return;
        Class<?> targetType = target.getClass();
        if (List.class.isAssignableFrom(targetType)) {
            List list = (List) target;
            for (int i = 0; i < list.size(); i++) {
                desensitizeProcessor.desensitize(list.get(i), qdbh, jyfwbh);
            }
        } else if (ResultDto.class.isAssignableFrom(targetType)) {
            ResultDto resultDto = (ResultDto) target;
            desensitizeProcessor.desensitize(resultDto.getValue(), qdbh, jyfwbh);
        } else if (ResultVo.class.isAssignableFrom(targetType)) {
            ResultVo resultVo = (ResultVo) target;
            desensitizeProcessor.desensitize(resultVo.getValue(), qdbh, jyfwbh);
        } else {
            desensitizeProcessor.desensitize(target, qdbh, jyfwbh);
        }
    }
}
