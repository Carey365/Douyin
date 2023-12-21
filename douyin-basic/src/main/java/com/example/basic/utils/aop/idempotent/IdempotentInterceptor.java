package com.example.basic.utils.aop.idempotent;
import com.example.basic.utils.RedisUtil;
import com.example.basic.utils.aop.idempotent.annotation.Idempotent;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 幂等拦截器
 */
@Aspect
@Component
@Slf4j
@Order(2)
public class IdempotentInterceptor {

    @Autowired
    private KeyResolver keyResolver;

    @Around("@annotation(com.example.basic.utils.aop.idempotent.annotation.Idempotent)")
    public Object invoke(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        if (!method.isAnnotationPresent(Idempotent.class)) {
            return null;
        }
        Idempotent idempotent = method.getAnnotation(Idempotent.class);
        String key;
        // 若没有配置 幂等 标识编号，则使用 方法名 + 参数列表作为区分
        if (!StringUtils.hasLength(idempotent.key())) {
            String methodName = method.getName();
            String argString = Arrays.asList(joinPoint.getArgs()).toString();
            key = methodName + argString;
        } else {
            // 使用jstl 规则区分
            key = keyResolver.resolver(idempotent, joinPoint);
        }
        // 当配置了el表达式但是所选字段为空时,会抛出异常,兜底使用方法名做标识
        if (key == null) {
            key = method.getName();
        }
        long expireTime = idempotent.expireTime();
        String info = idempotent.info();
        boolean lockNoWait = RedisUtil.acquireLock(key,expireTime);
        if (!lockNoWait) {
            log.warn("方法:{}重发请求", method);
            throw new Exception(info);
        }
        try {
            return joinPoint.proceed();
        } finally {
            RedisUtil.releaseLock(key);
        }
    }
}
