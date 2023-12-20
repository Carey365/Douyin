package com.example.basic.utils.aop.log;

import com.alibaba.fastjson.JSON;
import com.example.basic.utils.aop.log.PrintLog;
import com.example.basic.utils.response.ControllerResponse;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * @author carey
 * 日志打印切面类
 */
@Aspect
@Component
@Slf4j
public class PrintLogAspect {
    @Pointcut("@annotation(com.example.basic.utils.aop.log.PrintLog)")
    public void pointCut(){}
    @Around("pointCut()")
    public ControllerResponse around(ProceedingJoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature=(MethodSignature) signature;
        PrintLog printLogAnnotation = methodSignature.getMethod().getAnnotation(PrintLog.class);
        String methodName = printLogAnnotation.methodName();
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            log.info("{}方法入参为{}",methodName, JSON.toJSON(arg));
        }
        Object result=null;
        try {
            result=joinPoint.proceed();
        }
        catch (Throwable e) {
            log.info("执行方法发生异常，异常信息为{}", e.getMessage());
        }
        log.info("{}方法返参为{}",methodName,JSON.toJSON(result));
        return (ControllerResponse)result;
        // 对参数进行校验逻辑
        // 可以根据注解的属性进行不同的校验规则或错误信息处理
    }
}
