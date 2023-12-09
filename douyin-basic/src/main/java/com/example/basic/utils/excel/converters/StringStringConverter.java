package com.example.basic.utils.excel.converters;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
/**
 * Integer and string converter
 */
public class StringStringConverter implements Converter<String> {

    @Override
    public Class supportJavaTypeKey() {
        return String.class;
    }

    @Override
    public CellType supportExcelTypeKey() {
        return CellType.STRING;
    }

    @Override
    public String convertToJavaData(Cell cell) {
        return cell.getStringCellValue();
    }

}
