package com.example.basic.utils.excel.vo;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;

/**
 * ColumnVO对象
 *
 * @author chenlianghao5
 */
@Getter
@Setter
public class ColumnVO {

    /**
     * 列名
     */
    private String columnName;

    /**
     * 索引
     */
    private int index;
    /**
     * 字段filed
     */
    private Field field;

    /**
     * 时间转换格式 excelToList时候，会将String转换为Date;listToExcel时，会将Date转换为String。
     */
    private String dateFormat;
}

