package com.example.basic.utils.excel.vo;

import lombok.Data;

/**
 * sheet配置
 * @author chenlianghao5
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
