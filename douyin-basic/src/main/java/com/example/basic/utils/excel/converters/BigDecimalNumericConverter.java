package com.example.basic.utils.excel.converters;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import java.math.BigDecimal;
/**
 * Integer and string converter
 */
public class BigDecimalNumericConverter implements Converter<BigDecimal> {

    @Override
    public Class supportJavaTypeKey() {
        return BigDecimal.class;
    }

    @Override
    public CellType supportExcelTypeKey() {
        return CellType.NUMERIC;
    }

    @Override
    public BigDecimal convertToJavaData(Cell cell) {
        return BigDecimal.valueOf(cell.getNumericCellValue());
    }

}
