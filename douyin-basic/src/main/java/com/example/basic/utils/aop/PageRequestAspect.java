package com.example.basic.utils.aop;
import com.alibaba.fastjson.JSON;
import com.example.basic.eums.ResponsEums;
import com.example.basic.utils.response.PageResponse;
import com.example.basic.utils.response.PageResponseTransformer;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Objects;

/**
 * Controller统一处理切面
 */
@Aspect
@Component
@Slf4j
public class PageRequestAspect {


    @Around(value = "execution(* com.example.basic.controller.*Controller.*(..))")
    public Object process(ProceedingJoinPoint point) {
        String className = point.getTarget().getClass().getName();
        String methodName = point.getSignature().getName();
        Object[] args = point.getArgs();
        log.info("收到tenant admin请求:{}#{}入参{}", className, methodName, args);
        Object result = null;
        try {
            result = paramValid(point);
            if (result != null) {
                return result;
            }
            //调用底层方法
            result = point.proceed();
            return result;
        } catch (Throwable e) {
            //异常兜底捕获
            log.error(e.getMessage(), e);
            result = PageResponseTransformer.buildResponseNodata(ResponsEums.Faliure.getCode(),
                    ResponsEums.Faliure.getMsg());
            return result;
        } finally {
            log.info("收到tenant admin请求：{}#{} 响应为{}", className, methodName, JSON.toJSONString(result));
        }
    }

    /**
     * 功能描述:参数校验
     **/
    private PageResponse paramValid(ProceedingJoinPoint point) {
        Object[] args = point.getArgs();
        if (Objects.isNull(args)) {
            return null;
        }
        for (Object arg : args) {
            if (arg instanceof BindingResult) {
                BindingResult bindingResult = (BindingResult) arg;
                if (bindingResult.hasFieldErrors()) {
                    FieldError fieldError = bindingResult.getFieldError();
                    HashMap<String, String> fieldErrorMap = new HashMap<>();
                    assert fieldError != null;
                    fieldErrorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
                    return PageResponseTransformer.buildResponseWithFiledError(ResponsEums.Faliure.getCode(),
                            ResponsEums.Faliure.getMsg(), fieldErrorMap);
                }
                return null;
            }
        }
        return null;
    }


}
