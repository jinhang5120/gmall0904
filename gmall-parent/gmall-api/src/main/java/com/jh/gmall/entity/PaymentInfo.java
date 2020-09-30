package com.jh.gmall.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 支付信息表
 * </p>
 *
 * @author jin
 * @since 2020-09-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PaymentInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 对外业务编号
     */
    private String orderSn;

    /**
     * 订单编号
     */
    private String orderId;

    /**
     * 支付宝交易编号
     */
    private String alipayTradeNo;

    /**
     * 支付金额
     */
    private BigDecimal totalAmount;

    /**
     * 交易内容
     */
    private String subject;

    /**
     * 支付状态
     */
    private String paymentStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建时间
     */
    private Date confirmTime;

    /**
     * 回调信息
     */
    private String callbackContent;

    private Date callbackTime;


}
