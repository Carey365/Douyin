package com.example.basic.utils.excel.vo;

import lombok.Data;

/**
 * sheet配置
 *
 * @author zhouwucheng3
 * @date 2023/06/28
 */
@Data
public class SheetVO {
    /**
     * 表名字
     */
    private String sheetName;

    /**
     * 是否有表头
     */
    private Boolean hasHead;
}
