package com.example.basic.utils.excel.converters;

import javafx.util.converter.BigDecimalStringConverter;
import org.apache.poi.ss.usermodel.CellType;

import java.util.HashMap;
/**
 * 转换器工具
 */
public class ConverterUtil {
    /**
     * 转换器
     */
    private static HashMap<String, Converter> CONVERTER_MAP = new HashMap<>();

    static {
        register(new BigDecimalNumericConverter());
        register(new StringNumericConverter());
        register(new IntegerStringConverter());
        register(new IntegerNumericConverter());
        register(new StringStringConverter());
        register(new StringFormulaConverter());
    }

    public static void register(Converter converter) {
        CONVERTER_MAP.put(ConverterKeyBuild.buildKey(converter.supportJavaTypeKey(), converter.supportExcelTypeKey()),
                converter);
    }

    public static Converter matchConvert(Class clazz, CellType cellDataTypeEnum) {
        Converter converter = CONVERTER_MAP.get(ConverterKeyBuild.buildKey(clazz, cellDataTypeEnum));
        return converter;
    }
}

