package com.example.basic.utils;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.example.basic.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author carey
 */
@Aspect
@Component
@Slf4j
public class ParameterValidationAspect {
    @Pointcut("@annotation(com.example.basic.utils.ParameterValidation)")
    public void pointCut(){}

    @Around("pointCut()")
    public ControllerResponse around(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            System.out.println(arg);
            Map<String, String> map = ValidatorUtils.validate(arg);
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                System.out.println("键为：" + key);
                System.out.println("值为：" + value);
            }
            if (ObjectUtils.isNotEmpty(map)) {
                ControllerResponse response = new ControllerResponse();
                response.fail();
                return response;
            }
        }
        Object result=null;
        try {
            result=joinPoint.proceed();
        }
        catch (Throwable e) {
            log.info("执行方法发生异常，异常信息为{}", e.getMessage());
        }
        System.out.println("方法执行完毕");
        // 对参数进行校验逻辑
        // 可以根据注解的属性进行不同的校验规则或错误信息处理
        return (ControllerResponse)result;
    }

//    @After("pointCut()")
//    public void afterAdvice() {
//        // 在目标方法执行后执行的代码，无论成功还是失败
//        System.out.println("After advice: Performing cleanup actions...");
//    }

}
