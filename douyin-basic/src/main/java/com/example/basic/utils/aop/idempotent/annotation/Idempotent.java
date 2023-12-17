package com.example.basic.utils.aop.idempotent.annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 幂等
 *
 * @author ext.ruanchanglong1
 * @date 2023-09-25 17:07
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Idempotent {
    /**
     * <p>
     * 如果是实体类的话,默认拦截不会生效. objects.toString()会返回不同地址.
     * </p>
     * 幂等操作的唯一标识，使用spring el表达式 用#来引用方法参数
     * @return Spring-EL expression
     */
    String key() default "";

    /**
     * 有效期
     * 默认：1
     * 有效期要大于程序执行时间，否则请求还是可能会进来
     * 单位秒
     * @return
     */
    int expireTime() default 1;

    /**
     * 提示信息，可自定义
     * @return
     */
    String info() default "重复请求，请稍后重试";
}
