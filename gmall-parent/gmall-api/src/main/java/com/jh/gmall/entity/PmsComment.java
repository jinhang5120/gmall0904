package com.jh.gmall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 商品评价表
 * </p>
 *
 * @author jin
 * @since 2020-09-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PmsComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long productId;

    private String memberNickName;

    private String productName;

    /**
     * 评价星数：0->5
     */
    private Integer star;

    /**
     * 评价的ip
     */
    private String memberIp;

    private Date createTime;

    private Integer showStatus;

    /**
     * 购买时的商品属性
     */
    private String productAttribute;

    private Integer collectCouont;

    private Integer readCount;

    private String content;

    /**
     * 上传图片地址，以逗号隔开
     */
    private String pics;

    /**
     * 评论用户头像
     */
    private String memberIcon;

    private Integer replayCount;


}
