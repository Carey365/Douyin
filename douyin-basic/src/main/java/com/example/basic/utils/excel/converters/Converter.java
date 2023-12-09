package com.example.basic.utils.excel.converters;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
/**
 *
 type converter
 * @author zhanghui708
 * @date 2023/08/11
 */
public interface Converter<T> {
    /**
     * 支持java类型key
     *
     *
     * @return {@link Class }
     */
    Class supportJavaTypeKey();

    /**
     * 支持excel类型key
     *
     *
     * @return {@link CellType }
     */
    CellType supportExcelTypeKey();

    /**
     * 转换来java数据
     *
     * @param cell 细胞
     * @return {@link T }
     */
    T convertToJavaData(Cell cell) throws Exception;

}

