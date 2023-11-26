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
 * 贸易合同表
 * @TableName nav_trade_contract
 */
@TableName(value ="nav_trade_contract")
@Data
public class NavTradeContract implements Serializable {
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
     * 合同名称
     */
    @TableField(value = "contract_name")
    private String contractName;

    /**
     * 合同金额
     */
    @TableField(value = "contract_amount")
    private BigDecimal contractAmount;

    /**
     * 合同签订时间
     */
    @TableField(value = "contract_sign_date")
    private String contractSignDate;

    /**
     * 合同附件url
     */
    @TableField(value = "contract_url")
    private String contractUrl;

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