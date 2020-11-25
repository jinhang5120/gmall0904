package com.jh.gmall.service;

import com.jh.gmall.entity.PaymentInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 支付信息表 服务类
 * </p>
 *
 * @author jin
 * @since 2020-09-21
 */
public interface PaymentInfoService extends IService<PaymentInfo> {

    void insert(PaymentInfo paymentInfo);
}
