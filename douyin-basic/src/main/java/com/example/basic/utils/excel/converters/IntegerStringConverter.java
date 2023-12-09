package com.example.basic.utils.excel.converters;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
/**
 * Integer and string converter
 */
public class IntegerStringConverter implements Converter<Integer> {

    @Override
    public Class supportJavaTypeKey() {
        return Integer.class;
    }

    @Override
    public CellType supportExcelTypeKey() {
        return CellType.STRING;
    }

    @Override
    public Integer convertToJavaData(Cell cell) {
        return Integer.valueOf(cell.getStringCellValue());
    }

}
