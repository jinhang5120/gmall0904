package com.jh.gmall.service;

import com.jh.gmall.entity.PmsProductSaleAttrValue;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * spu销售属性值 服务类
 * </p>
 *
 * @author jin
 * @since 2020-09-21
 */
public interface PmsProductSaleAttrValueService extends IService<PmsProductSaleAttrValue> {

    void saveProductSaleAttrValue(PmsProductSaleAttrValue pmsProductSaleAttrValue);
}
