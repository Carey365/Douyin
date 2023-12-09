package com.example.basic.utils.excel.converters;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
/**
 * Formula conerter
 */
public class StringFormulaConverter implements Converter<String> {
    @Override
    public Class supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public CellType supportExcelTypeKey() {
        return CellType.FORMULA;
    }

    @Override
    public String convertToJavaData(Cell cell) throws Exception {
        String value;
        try {
            value = String.valueOf(cell.getNumericCellValue());
        } catch (IllegalStateException e) {
            value = String.valueOf(cell.getRichStringCellValue());
        }
        return value;
    }
}
