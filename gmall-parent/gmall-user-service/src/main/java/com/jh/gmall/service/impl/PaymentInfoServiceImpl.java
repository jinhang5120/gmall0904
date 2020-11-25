package com.jh.gmall.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.jh.gmall.entity.PaymentInfo;
import com.jh.gmall.mapper.PaymentInfoMapper;
import com.jh.gmall.service.PaymentInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 支付信息表 服务实现类
 * </p>
 *
 * @author jin
 * @since 2020-09-21
 */
@Service
public class PaymentInfoServiceImpl extends ServiceImpl<PaymentInfoMapper, PaymentInfo> implements PaymentInfoService {
    @Autowired
    PaymentInfoMapper paymentInfoMapper;
    @Override
    public void insert(PaymentInfo paymentInfo) {
        paymentInfoMapper.insert(paymentInfo);
    }
}
