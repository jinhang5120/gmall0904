package com.jh.gmall.service;

import com.jh.gmall.entity.OmsOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jh.gmall.entity.OmsOrderItem;

import java.util.List;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author jin
 * @since 2020-09-21
 */
public interface OmsOrderService extends IService<OmsOrder> {

    String checkTradeCode(Long memberId, String tradeCode);

    String getTradeCode(Long memberId);

    OmsOrder saveOrder(OmsOrder omsOrder, List<OmsOrderItem> omsOrderItems);

    OmsOrder selectById(Long orderId);
}
