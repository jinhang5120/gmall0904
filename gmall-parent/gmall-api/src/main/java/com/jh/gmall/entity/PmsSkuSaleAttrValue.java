package com.jh.gmall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * sku销售属性值
 * </p>
 *
 * @author jin
 * @since 2020-09-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PmsSkuSaleAttrValue implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 库存单元id
     */
    private Long skuId;

    /**
     * 销售属性id（冗余)
     */
    private Long saleAttrId;

    /**
     * 销售属性值id
     */
    private Long saleAttrValueId;

    /**
     * 销售属性名称(冗余)
     */
    private String saleAttrName;

    /**
     * 销售属性值名称(冗余)
     */
    private String saleAttrValueName;


}
