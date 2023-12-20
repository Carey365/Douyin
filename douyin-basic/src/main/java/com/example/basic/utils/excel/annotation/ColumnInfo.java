package com.example.basic.utils.excel.annotation;

import java.lang.annotation.*;
/**
 *
 excel行注解
 * @author chenlianghao5
 */
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ColumnInfo {

    /**
     * 列名
     */
    String columnName();

    /**
     * 列顺序
     */
    int index() default 0;

    /**
     * 是否隐藏
     */
    boolean hidden() default false;

    /**
     * 是否必须
     */
    boolean mandatory() default false;

    /**
     * 联动数据的来源索引
     *
     * @return
     */
    int chainFrom() default -1;

    /**
     * 页面列表title name
     *
     * @return
     */
    String titleName() default "";

    /**
     * 页面列表title 描述
     *
     * @return
     */
    String desc() default "";

    /**
     * 指定时间转换格式，excelToList时候，会将String转换为Date;listToExcel时，会将Date转换为String。
     *
     * @return
     */
    String dateFormat() default "";
}
