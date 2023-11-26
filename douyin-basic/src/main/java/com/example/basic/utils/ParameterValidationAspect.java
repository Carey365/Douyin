package com.example.basic.utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Map;

@Aspect
@Component
public class ParameterValidationAspect {
    @Pointcut("@annotation(com.example.basic.utils.ParameterValidation)")
    public void pointCut(){}

    @Before("pointCut()")
    public void before(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        for (int i=0;i<args.length;i++){
            System.out.println(args[i]);
            Map<String,String> map=ValidatorUtils.validate(args[i]);
            for (Map.Entry<String,String> entry:map.entrySet()){
                String key=entry.getKey();
                String value=entry.getValue();
                System.out.println("键为："+key);
                System.out.println("值为："+value);
            }
        }
        // 对参数进行校验逻辑
        // 可以根据注解的属性进行不同的校验规则或错误信息处理
    }
}
