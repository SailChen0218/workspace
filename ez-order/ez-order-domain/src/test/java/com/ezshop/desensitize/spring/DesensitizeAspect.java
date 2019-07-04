package com.ezshop.desensitize.spring;

import com.alibaba.fastjson.JSON;
import com.ezshop.desensitize.DesensitizeProcessor;
import com.ezshop.desensitize.ValidateProcessor;
import com.ezshop.desensitize.dto.ErrorDto;
import com.ezshop.desensitize.exception.DesensitizeFailedException;
import com.ezshop.desensitize.exception.ValidateFailedException;
import com.ezshop.desensitize.util.ReflectionUtils;
import com.ezshop.test.ResultDto;
import com.ezshop.test.ResultVo;
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
            // 获取校验目标接口方法
            Object target = joinPoint.getTarget();
            Signature signature = joinPoint.getSignature();
            MethodSignature methodSignature = (MethodSignature)signature;
            Method targetMethod = methodSignature.getMethod();
            Method targetInterfaceMethod = ReflectionUtils.getInterfaceMethod(
                    targetMethod.getDeclaringClass(), methodSignature.getMethod());

            // 校验接口方法请求参数
            List<String> parameterNames = ReflectionUtils.getMethodParameterNames(targetInterfaceMethod);
            List<ErrorDto> errorDtoList = validateProcessor.validateParameters(target,
                    targetInterfaceMethod, args, parameterNames, channel, service);
            if (errorDtoList.size() != 0) {
                Class<?> methodReturnType = targetInterfaceMethod.getReturnType();
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
