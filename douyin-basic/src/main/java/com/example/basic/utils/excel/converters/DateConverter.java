package com.example.basic.utils.excel.converters;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 *

 日期转换器
 * @author zhanghui708
 * @date 2023/08/15
 */
@Slf4j
public class DateConverter implements Converter<Date> {

    private String fmt;

    public DateConverter(String fmt) {
        this.fmt = fmt;
    }

    @Override
    public Class supportJavaTypeKey() {
        return Date.class;
    }

    @Override
    public CellType supportExcelTypeKey() {
        return CellType.STRING;
    }

    @Override
    public Date convertToJavaData(Cell cell) {
        String stringCellValue = cell.getStringCellValue();
        SimpleDateFormat sdf = new SimpleDateFormat(this.fmt);
        try {
            Date parse = sdf.parse(stringCellValue);
            return parse;
        } catch (ParseException e) {
            log.error("解析excel日期转换异常，fmt:{},cellValue:{}", this.fmt, stringCellValue, e);
        }
        return null;
    }

}
