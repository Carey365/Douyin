package com.example.basic.utils.excel.converters;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
/**
 * Integer and string converter
 */
public class StringNumericConverter implements Converter<String> {

    private static final String INT_VAL_POSTFIX = ".0";

    @Override
    public Class supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public CellType supportExcelTypeKey() {
        return CellType.NUMERIC;
    }

    @Override
    public String convertToJavaData(Cell cell) {
        Object value = null;
        double cellValue = cell.getNumericCellValue();
        long longVal = Math.round(cellValue);
        if (Double.parseDouble(longVal + INT_VAL_POSTFIX) == cellValue) {
            value = longVal;
        } else {
            value = cellValue;
        }
        return String.valueOf(value);
    }

}
