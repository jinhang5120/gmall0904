package com.jh.gmall.service;

import com.jh.gmall.entity.PmsSkuSaleAttrValue;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * sku销售属性值 服务类
 * </p>
 *
 * @author jin
 * @since 2020-09-21
 */
public interface PmsSkuSaleAttrValueService extends IService<PmsSkuSaleAttrValue> {

    void insertSkuSaleAttrValue(PmsSkuSaleAttrValue pmsSkuSaleAttrValue);

    List<PmsSkuSaleAttrValue> selectListBySkuId(Long skuId);
}
