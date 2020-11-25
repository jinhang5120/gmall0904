package com.jh.gmall.service;

import com.jh.gmall.entity.OmsCartItem;
import com.jh.gmall.entity.OmsOrderItem;
import com.jh.gmall.entity.PmsSkuInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 库存单元表 服务类
 * </p>
 *
 * @author jin
 * @since 2020-09-21
 */
public interface PmsSkuInfoService extends IService<PmsSkuInfo> {

    Long saveSkuInfo(PmsSkuInfo pmsSkuInfo);

    PmsSkuInfo selectOne(Long skuId, String remoteAddr);

    List<PmsSkuInfo> selectListByProductId(Long productId);

    List<PmsSkuInfo> selectAll();

    boolean checkPrice(Long productSkuId, BigDecimal productPrice);
}
