package com.example.basic.utils.excel.converters;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
/**
 * Integer and string converter
 */
public class IntegerNumericConverter implements Converter<Integer> {

    @Override
    public Class supportJavaTypeKey() {
        return Integer.class;
    }

    @Override
    public CellType supportExcelTypeKey() {
        return CellType.NUMERIC;
    }

    @Override
    public Integer convertToJavaData(Cell cell) {
        return (int)cell.getNumericCellValue();
    }

}
