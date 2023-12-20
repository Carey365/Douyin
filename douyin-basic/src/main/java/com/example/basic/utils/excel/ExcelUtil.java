package com.example.basic.utils.excel;
import com.example.basic.utils.excel.annotation.ColumnInfo;
import com.example.basic.utils.excel.converters.Converter;
import com.example.basic.utils.excel.converters.ConverterUtil;
import com.example.basic.utils.excel.converters.DateConverter;
import com.example.basic.utils.excel.vo.ColumnVO;
import com.example.basic.utils.excel.vo.SheetVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

/**
 * Excel工具类
 *
 */
@Slf4j
public class ExcelUtil {
    /**
     * excel文件后缀
     */
    private static final String EXCEL_FILE_SUFFIX = ".xlsx";
    private static final int SHEET_SIZE = 500_000;
    private static final int LIMIT_ROW_SIZE = 5000;
    public static final String ATTACHMENT_FILENAME_S_FILENAME_UTF_8_S = "attachment;filename=%s;filename*=utf-8''%s";
    public static final String APPLICATION_VND_MS_EXCEL_CHARSET_UTF_8 = "application/vnd.ms-excel;charset=utf-8";
    public static final String CONTENT_DISPOSITION = "Content-Disposition";
    public static final String EXTRA_FILENAME = "extra-filename";

    /**
     * 功能描述:  传入byte[]输出list对象
     *
     * @param bytes
     * @param clazz
     * @return {@link List }<{@link T }>
     */
    public static <T> List<T> readWorkbookFromBytes(byte[] bytes, Class clazz) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        return readWorkbook(byteArrayInputStream, clazz);
    }

    /**
     * 功能描述:  传入byte[]和sheet页码输出list对象
     *
     * @param bytes
     * @param clazz
     * @param num   编号
     * @return {@link List }<{@link T }>
     */
    public static <T> List<T> readWorkbookFromBytes(byte[] bytes, Class clazz, int num) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        return readOneWorkbook(byteArrayInputStream, clazz, num);
    }

    /**
     * 功能描述: excel解析为列表
     *
     * @param inputStream
     * @param clazz
     * @return {@link List }<{@link T }>
     */
    public static <T> List<T> readWorkbook(InputStream inputStream, Class clazz) {
        XSSFWorkbook xssfWorkbook = null;
        List<T> resultList = new ArrayList<>();
        try {
            xssfWorkbook = new XSSFWorkbook(inputStream);
            Iterator<Sheet> sheetIterator = xssfWorkbook.sheetIterator();
            while (sheetIterator.hasNext()) {
                parseSheet(sheetIterator.next(), resultList, clazz);
            }
        } catch (Exception e) {
            log.error("excel解析异常", e);
        }
        return resultList;
    }

    /**
     * 功能描述: excel解析为列表(解析指定的sheet)
     *
     * @param inputStream
     * @param clazz
     * @param sheetNum    sheet编号
     * @return {@link List }<{@link T }>
     */
    public static <T> List<T> readOneWorkbook(InputStream inputStream, Class clazz, int sheetNum) {
        XSSFWorkbook xssfWorkbook = null;
        List<T> resultList = new ArrayList<>();
        try {
            xssfWorkbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheetAt = xssfWorkbook.getSheetAt(sheetNum);
            parseSheet(sheetAt, resultList, clazz);
        } catch (Exception e) {
            log.error("excel解析异常", e);
        }
        return resultList;
    }

    /**
     * 功能描述: 输出多list成excel
     *
     * @param outputStream 输出流
     * @param t            t  类型为多List
     * @param sheetVoList  sheet配置列表
     */
    public static <T> void writeToExcel(OutputStream outputStream, T t, List<SheetVO> sheetVoList) {
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook(xssfWorkbook, SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        try {
            Class clazz = t.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                SheetVO sheetVO = sheetVoList.get(i);
                Field f = fields[i];
                f.setAccessible(true);
                List<?> fieldValue = (List<?>)f.get(t);
                if (f.getType() == java.util.List.class) {
                    // 如果是List类型，得到其Generic的类型
                    Type genericType = f.getGenericType();
                    if (genericType == null) {
                        continue;
                    }
                    // 如果是泛型参数的类型
                    if (genericType instanceof ParameterizedType) {
                        ParameterizedType pt = (ParameterizedType)genericType;
                        //得到泛型里的class类型对象
                        Class<?> genericClazz = (Class<?>)pt.getActualTypeArguments()[0];
                        ExcelUtil.createWorkSheet(sxssfWorkbook, fieldValue, genericClazz, sheetVO);
                    }
                }
            }
            sxssfWorkbook.write(outputStream);
        } catch (Exception e) {
            log.error("导出列表异常{}", e);
        } finally {
            if (null != sxssfWorkbook) {
                sxssfWorkbook.dispose();
            }
        }
    }

    /**
     * 创建工作表
     *
     * @param sxssfWorkbook sxssf工作簿
     * @param list          列表
     * @param clazz         clazz
     * @param sheetVO       sheet配置
     * @return {@link SXSSFSheet }
     */
    public static <T> SXSSFSheet createWorkSheet(SXSSFWorkbook sxssfWorkbook, final List<T> list, Class clazz,
                                                 SheetVO sheetVO) {
        try {
            SXSSFSheet sheet = sxssfWorkbook.createSheet(sheetVO.getSheetName());
            SXSSFRow row = sheet.createRow(0);
            SXSSFCell cell = null;
            List<String> columnNames = getOrderColumnNames(clazz);
            int offset = 0;
            int rowIndex = 0;
            if (sheetVO.getHasHead()) {
                // 插入第一行数据的表头
                createHead(clazz, sheet);
                rowIndex++;
            }

            while (offset < list.size()) {
                createColumn(columnNames, list.get(offset), sheet, rowIndex);
                rowIndex++;
                offset++;
                if (offset == SHEET_SIZE) {
                    break;
                }
            }
            return sheet;
        } catch (Exception e) {
            log.error("workbook创建异常{}", e);
        }
        return null;
    }

    /**
     * 解析sheet为指定数据结构到列表中
     * @param sheet
     * @param list  列表
     * @param clazz
     */
    private static <T> void parseSheet(Sheet sheet, List<T> list, Class clazz) throws Exception {
        List<String> headList = new ArrayList<>();
        List<ColumnVO> columnVOList = parseColumn(clazz);
        boolean isHead = true;
        Iterator<Row> rowIterator = sheet.rowIterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (isHead) {
                //校验模板行头
                columnVOList.forEach(columnVO -> {
                    Cell cell = row.getCell(columnVO.getIndex());
                    if (!columnVO.getColumnName().equals(cell.getStringCellValue())) {
                        log.error("模板行映射异常{},{}", columnVO.getColumnName(), cell.getStringCellValue());
                    }
                });
                row.forEach(cell -> headList.add(cell.getStringCellValue()));
                isHead = false;
                continue;
            }
            T object = (T)clazz.newInstance();
            int j = 0;
            int emptySize = 0;
            for (ColumnVO columnVO : columnVOList) {
                Cell cell = row.getCell(columnVO.getIndex());
                if (cell == null || CellType.BLANK.equals(cell.getCellType())) {
                    emptySize++;
                    continue;
                }
                Field field = columnVO.getField();
                if (null == field) {
                    log.info("字段:{}, 无法解析", headList.get(j));
                }
                Converter converter = ConverterUtil.matchConvert(field.getType(), cell.getCellType());
                if (null == converter) {
                    log.info("第%s行格式有误", cell.getRowIndex() + 1);
                }
                if (StringUtils.isNotBlank(columnVO.getDateFormat())) {
                    converter = new DateConverter(columnVO.getDateFormat());
                }
                field.set(object, converter.convertToJavaData(cell));
                j++;
            }
            //判断列都为空，则是空行
            if (emptySize != columnVOList.size()) {
                list.add(object);
            }
        }
    }

    /**
     * 功能描述: 输出list到HttpServletResponse
     * @param response
     * @param list     列表
     * @param clazz
     * @param fileName file名称
     */
    public static <T> void writeToHttpServletResponse(HttpServletResponse response, List<T> list, Class<T> clazz,
                                                      String fileName) {
        fileName = fileName.endsWith(EXCEL_FILE_SUFFIX) ? fileName : fileName.concat(EXCEL_FILE_SUFFIX);
        try {
            fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setContentType(APPLICATION_VND_MS_EXCEL_CHARSET_UTF_8);
        response
                .setHeader(CONTENT_DISPOSITION, String.format(ATTACHMENT_FILENAME_S_FILENAME_UTF_8_S, fileName, fileName));
        response.addHeader(EXTRA_FILENAME, fileName);
        try {
            ExcelUtil.writeToExcel(response.getOutputStream(), list, clazz);
        } catch (IOException e) {
            log.error("模板导出异常", e);
        }
    }

    /**
     * 写出excel到outputStream
     *
     * @param outputStream
     * @param list
     * @param clazz
     */
    public static <T> void writeToExcel(OutputStream outputStream, List<T> list, Class clazz) {
        SXSSFWorkbook sxssfWorkbook = null;
        try {
            sxssfWorkbook = createWorkbook(list, clazz, true);
            sxssfWorkbook.write(outputStream);
        } catch (Exception e) {
            log.info("excel解析异常", e);
        } finally {
            if (null != sxssfWorkbook) {
                sxssfWorkbook.dispose();
            }
        }
    }

    /**
     * 写出excel模板到outputStream
     *
     * @param outputStream
     * @param clazz
     * @param <T>
     */
    public static <T> void writeToExcelTemplate(OutputStream outputStream, Class clazz) {
        SXSSFWorkbook sxssfWorkbook = null;
        try {
            sxssfWorkbook = createWorkbookTemplate(clazz, true);
            sxssfWorkbook.write(outputStream);
        } catch (Exception e) {
            log.info("excel解析异常", e);
        } finally {
            if (null != sxssfWorkbook) {
                sxssfWorkbook.dispose();
            }
        }
    }

    /**
     * 创建workbook
     *
     * @param list
     * @param clazz
     * @param hasHead
     * @return {@link SXSSFWorkbook }
     */
    public static <T> SXSSFWorkbook createWorkbook(final List<T> list, Class clazz, boolean hasHead)
            throws NoSuchFieldException, IllegalAccessException {
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook(xssfWorkbook, SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        int sheetIndex = 0;
        List<String> columnNames = getOrderColumnNames(clazz);
        // 数据偏移量
        int offset = 0;
        do {
            int rowIndex = 0;
            int maxSize = (sheetIndex + 1) * SHEET_SIZE;
            SXSSFSheet sheet = sxssfWorkbook.createSheet("sheet_" + sheetIndex++);
            if (hasHead) {
                createHead(clazz, sheet);
                rowIndex++;
            }
            while (offset < list.size()) {
                createColumn(columnNames, list.get(offset), sheet, rowIndex);
                rowIndex++;
                offset++;
                if (offset == maxSize) {
                    break;
                }
            }
        } while (offset < list.size());
        return sxssfWorkbook;
    }

    /**
     * 创建workbook模板
     *
     * @param clazz
     * @param hasHead
     * @param <T>
     * @return
     */
    public static <T> SXSSFWorkbook createWorkbookTemplate(Class clazz, boolean hasHead) {
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        SXSSFWorkbook sxssfWorkbook = new SXSSFWorkbook(xssfWorkbook, SXSSFWorkbook.DEFAULT_WINDOW_SIZE);
        // 创建XSSFCellStyle实例
        XSSFCellStyle style = xssfWorkbook.createCellStyle();
        // 将单元格格式设置为文本类型
        style.setDataFormat(xssfWorkbook.createDataFormat().getFormat("@"));
        int sheetIndex = 0;
        List<String> columnNames = getOrderColumnNames(clazz);
        // 数据偏移量
        int offset = 0;
        do {
            int rowIndex = 0;
            int maxSize = (sheetIndex + 1) * SHEET_SIZE;
            SXSSFSheet sheet = sxssfWorkbook.createSheet("sheet_" + sheetIndex++);
            if (hasHead) {
                createHead(clazz, sheet);
                rowIndex++;
            }
            while (offset < 100) {
                createColumnTemplate(columnNames, sheet, style, rowIndex);
                rowIndex++;
                offset++;
                if (offset == maxSize) {
                    break;
                }
            }
        } while (offset < 1);
        return sxssfWorkbook;
    }

    /**
     * @param <T>
     * @param columns
     * @param sheet
     * @param style
     * @param rowNum
     */
    private static <T> void createColumnTemplate(List<String> columns, SXSSFSheet sheet, XSSFCellStyle style, int rowNum) {
        SXSSFRow row = sheet.createRow(rowNum);
        int index = 0;
        for (String column : columns) {
            SXSSFCell cell = row.createCell(index);
            cell.setCellStyle(style);
            index++;
        }
    }

    /**
     * 根据指定的列名顺序写入行
     *
     * @param columns
     * @param object
     * @param sheet
     * @param rowNum
     */
    private static <T> void createColumn(List<String> columns, T object, SXSSFSheet sheet, int rowNum)
            throws NoSuchFieldException, IllegalAccessException {
        SXSSFRow row = sheet.createRow(rowNum);
        int index = 0;
        for (String column : columns) {
            Field field = object.getClass().getDeclaredField(column);
            field.setAccessible(true);
            String value = ObjectUtils.isEmpty(field.get(object)) ? "" : field.get(object).toString();
            SXSSFCell cell = row.createCell(index, CellType.STRING);
            cell.setCellType(CellType.STRING);
            cell.setCellValue(value);
            index++;
        }
    }

    /**
     * 解析有columnInfo注解的field，根据index排序后输出fieldName列表
     *
     * @param clazz
     * @return {@link List }<{@link String }>
     */
    private static List<String> getOrderColumnNames(Class clazz) {
        List<Field> list = Arrays.asList(clazz.getDeclaredFields());
        log.info("listsize:{}", list == null ? 0 : list.size());
        List<String> annotationField = list.stream().filter(v -> v.isAnnotationPresent(ColumnInfo.class))
                .sorted(Comparator.comparing(field -> field.getAnnotation(ColumnInfo.class).index())).map(Field::getName)
                .collect(toList());
        return annotationField;
    }

    /**
     * 生成表头，后续增加注解解析表头格式
     *
     * @param clazz
     * @param sheet
     */
    private static void createHead(Class clazz, SXSSFSheet sheet) {
        List<String> orderHeadNames = getOrderHeadNames(clazz);
        Map<String, String> orderHeadName2Descs = getOrderHeadDescs(clazz);
        Map<String, Field> nameField = getName2Field(clazz);
        int colIdx = 0;
        int rowIdx = 0;
        SXSSFRow row = sheet.createRow(rowIdx);
        sheet.setDefaultColumnWidth(orderHeadNames.size());
        for (String v : orderHeadNames) {
            SXSSFCell cell = row.createCell(colIdx);
            if (nameField.get(v).getAnnotation(ColumnInfo.class).hidden()) {
                sheet.setColumnHidden(colIdx, true);
            }
            CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
            Font font = sheet.getWorkbook().createFont();
            font.setBold(true);
            cellStyle.setFont(font);
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setWrapText(true);
            cell.setCellStyle(cellStyle);
            //设置表头为名称
            cell.setCellValue(v);
            //检查是否需要追加表头注释
            String headerDesc = orderHeadName2Descs.get(v);
            if (StringUtils.isNotBlank(headerDesc)) {
                try {
                    Drawing drawing = cell.getSheet().createDrawingPatriarch();
                    CreationHelper factory = cell.getSheet().getWorkbook().getCreationHelper();
                    ClientAnchor anchor = factory.createClientAnchor();
                    anchor.setCol1(cell.getColumnIndex());
                    anchor.setCol2(cell.getColumnIndex() + 1);
                    anchor.setRow1(cell.getRowIndex());
                    anchor.setRow2(cell.getRowIndex() + 7);
                    Comment comment = drawing.createCellComment(anchor);
                    RichTextString str = factory.createRichTextString(headerDesc);
                    comment.setString(str);
                    cell.setCellComment(comment);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
            colIdx++;
        }
    }

    /**
     * 解析有columnInfo注解的field，根据index排序后输出列名列表
     *
     * @param clazz
     * @return {@link List }<{@link String }>
     */
    public static List<String> getOrderHeadNames(Class clazz) {
        List<Field> list = Arrays.asList(clazz.getDeclaredFields());
        List<String> annotationField = list.stream().filter(v -> v.isAnnotationPresent(ColumnInfo.class))
                .sorted(Comparator.comparing(field -> field.getAnnotation(ColumnInfo.class).index()))
                .map(v -> v.getAnnotation(ColumnInfo.class).columnName()).collect(toList());
        return annotationField;
    }

    public static Map<String, String> getOrderHeadDescs(Class clazz) {
        List<Field> list = Arrays.asList(clazz.getDeclaredFields());
        Map<String, String> annotationField = list.stream().filter(v -> v.isAnnotationPresent(ColumnInfo.class))
                .collect(toMap(v -> v.getAnnotation(ColumnInfo.class).columnName(),
                        v -> v.getAnnotation(ColumnInfo.class).desc()));
        return annotationField;
    }

    /**
     * 解析class 含有columnInfo注解的field
     *
     * @param clazz
     * @return {@link Map }<{@link String }, {@link Field }>
     */
    public static Map<String, Field> getName2Field(Class clazz) {
        List<Field> list = Arrays.asList(clazz.getDeclaredFields());
        Map<String, Field> annotationField = list.stream().filter(v -> v.isAnnotationPresent(ColumnInfo.class))
                .collect(toMap(v -> v.getAnnotation(ColumnInfo.class).columnName(), v -> {
                    v.setAccessible(true);
                    return v;
                }));
        return annotationField;
    }

    /**
     * 功能描述: 解析class对象的column
     * @param clazz
     * @return {@link List }<{@link ColumnVO }>
     */
    public static List<ColumnVO> parseColumn(Class clazz) {
        List<Field> list = Arrays.asList(clazz.getDeclaredFields());
        List<ColumnVO> columnVOList =
                list.stream().filter(field -> field.isAnnotationPresent(ColumnInfo.class)).map(field -> {
                    ColumnInfo annotation = field.getAnnotation(ColumnInfo.class);
                    ColumnVO columnVO = new ColumnVO();
                    columnVO.setColumnName(annotation.columnName());
                    columnVO.setIndex(annotation.index());
                    columnVO.setDateFormat(annotation.dateFormat());
                    field.setAccessible(true);
                    columnVO.setField(field);
                    return columnVO;
                }).collect(toList());
        return columnVOList;
    }
}
