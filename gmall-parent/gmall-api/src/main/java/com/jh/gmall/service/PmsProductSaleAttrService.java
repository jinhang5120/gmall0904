package com.jh.gmall.service;

import com.jh.gmall.entity.PmsProductSaleAttr;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jin
 * @since 2020-09-21
 */
public interface PmsProductSaleAttrService extends IService<PmsProductSaleAttr> {

    PmsProductSaleAttr saveProductSaleAttr(PmsProductSaleAttr pmsProductSaleAttr);

    List<PmsProductSaleAttr> spuSaleAttrList(Long spuId);

    List<PmsProductSaleAttr> selectListByProductId(Long productId);
}
