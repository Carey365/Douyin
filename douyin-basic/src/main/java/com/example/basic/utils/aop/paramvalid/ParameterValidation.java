package com.example.basic.utils.aop.paramvalid;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 参数校验注解
 * @author chenlianghao
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ParameterValidation {
    // 可以添加一些属性，用于指定校验规则或错误信息
}
