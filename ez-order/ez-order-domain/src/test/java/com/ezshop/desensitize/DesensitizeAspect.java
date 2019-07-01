package com.ezshop.desensitize;

import com.alibaba.fastjson.JSON;
import com.ezshop.desensitize.dto.ErrorDto;
import com.ezshop.test.ResultDto;
import com.ezshop.test.ResultVo;
import io.swagger.annotations.ApiParam;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

@Aspect
@Component
public class DesensitizeAspect {

    @Autowired
    DesensitizeProcessor desensitizeProcessor;

    @Autowired
    ValidateProcessor validateProcessor;

    @Pointcut("@annotation(com.ezshop.desensitize.Desensitized)")
    public void desensitized() {
    }

    @Around("desensitized()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        // 获取渠道编号 //TODO
        String channel = "";

        // 获取交易服务编号 //TODO
        String service = "";

        // 方法执行前进行入参验证
        Object result = this.validateJoinPoit(joinPoint, channel, service);
        if (result != null) {
            return result;
        }

        // 执行方法
        Object object = joinPoint.proceed();

        // 对返回值进行脱敏处理
        desensitize(object, channel, service);

        System.out.println(JSON.toJSONString(object));
        return object;
    }

    /**
     * 校验方法入参
     *
     * @param joinPoint
     * @return
     * @throws ValidateFailedException
     */
    private Object validateJoinPoit(JoinPoint joinPoint, String channel, String service) throws ValidateFailedException {
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            Object target = joinPoint.getTarget();
            Signature signature = joinPoint.getSignature();
            MethodSignature methodSignature = (MethodSignature)signature;
            Method targetMethod = methodSignature.getMethod();
            String[] argNames = getMethodArgNames(targetMethod);
            List<ErrorDto> errorDtoList = validateProcessor.validateParameters(target,
                    targetMethod, args, argNames, channel, service);
            if (errorDtoList.size() != 0) {
                Class<?> methodReturnType = targetMethod.getReturnType();
                if (ResultDto.class.isAssignableFrom(methodReturnType)) {
                    ResultDto resultDto = ResultDto.valueOfError("请求参数验证失败。");
                    resultDto.getResultMap().put("errors", errorDtoList);
                    return resultDto;
                } else if (ResultVo.class.isAssignableFrom(methodReturnType)) {
                    ResultVo resultVo = ResultVo.valueOfError("请求参数验证失败。");
                    resultVo.getResultMap().put("errors", errorDtoList);
                    return resultVo;
                } else {
                    throw new ValidateFailedException(errorDtoList,
                            "请求参数验证失败。" + JSON.toJSONString(errorDtoList));
                }
            }
        }
        return null;
    }

    /**
     * 获取方法参数名称
     *
     * @param method
     * @return
     * @throws ValidateFailedException
     */
    private String[] getMethodArgNames(Method method) throws ValidateFailedException {
        Parameter[] parameters = method.getParameters();
        if (parameters != null && parameters.length > 0) {
            String[] argNames = new String[parameters.length];
            for (int i = 0; i < parameters.length; i++) {
                ApiParam apiParams = parameters[i].getAnnotation(ApiParam.class);
                if (apiParams == null) {
                    throw new ValidateFailedException(method.getName() +
                            "方法参数缺少ApiParam注解。例：@ApiParam(name = \"djxh\", value = \"登记序号\")");
                } else {
                    argNames[i] = apiParams.name();
                }
            }
            return argNames;
        }
        return null;
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
        if (ResultDto.class.isAssignableFrom(targetType)) {
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
