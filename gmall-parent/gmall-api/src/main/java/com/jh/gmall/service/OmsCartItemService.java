package com.jh.gmall.service;

import com.jh.gmall.entity.OmsCartItem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 购物车表 服务类
 * </p>
 *
 * @author jin
 * @since 2020-09-21
 */
public interface OmsCartItemService extends IService<OmsCartItem> {

    void addCartItem(OmsCartItem omsCartItem);

    void updateCart(OmsCartItem omsCartItemFromDb);

    void syncCache(Long memberId);

    OmsCartItem selectOneFromCart(Long memberId, Long skuId);

    List<OmsCartItem> selectListByMemberId(long parseLong);

    void updateIsCheckedByProductSkuId(Long productSkuId, Integer isChecked);
}
