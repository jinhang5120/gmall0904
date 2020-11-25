package com.jh.gmall.service;

import com.jh.gmall.entity.UmsMemberReceiveAddress;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 会员收货地址表 服务类
 * </p>
 *
 * @author jin
 * @since 2020-09-21
 */
public interface UmsMemberReceiveAddressService extends IService<UmsMemberReceiveAddress> {

    List<UmsMemberReceiveAddress> selectByMemberId(Long memberId);
}
