package com.example.basic.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 贸易发票表
 * @TableName nav_trade_invoice
 */
@TableName(value ="nav_trade_invoice")
@Data
public class NavTradeInvoice implements Serializable {
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 内部订单单号
     */
    @TableField(value = "subject_no")
    private String subjectNo;

    /**
     * 应收账款编号
     */
    @TableField(value = "order_no")
    private String orderNo;

    /**
     * 合同编号
     */
    @TableField(value = "contract_no")
    private String contractNo;

    /**
     * 发票号码
     */
    @TableField(value = "invoice_no")
    private String invoiceNo;

    /**
     * 发票代码
     */
    @TableField(value = "invoice_code")
    private String invoiceCode;

    /**
     * 开票日期
     */
    @TableField(value = "invoice_date")
    private String invoiceDate;

    /**
     * 买方名称
     */
    @TableField(value = "buyer_name")
    private String buyerName;

    /**
     * 卖方名称
     */
    @TableField(value = "seller_name")
    private String sellerName;

    /**
     * 开票金额
     */
    @TableField(value = "invoice_amount")
    private BigDecimal invoiceAmount;

    /**
     * 货物名称
     */
    @TableField(value = "invoice_name")
    private String invoiceName;

    /**
     * 发票附件url
     */
    @TableField(value = "invoice_url")
    private String invoiceUrl;

    /**
     * 租户编号
     */
    @TableField(value = "tenant_no")
    private String tenantNo;

    /**
     * 创建时间
     */
    @TableField(value = "created_date")
    private Date createdDate;

    /**
     * 修改时间
     */
    @TableField(value = "modified_date")
    private Date modifiedDate;

    /**
     * 逻辑删除标识,0未删，1删除
     */
    @TableField(value = "deleted")
    private Integer deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}