package com.jh.gmall.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jh.gmall.entity.UmsMemberReceiveAddress;
import com.jh.gmall.mapper.UmsMemberReceiveAddressMapper;
import com.jh.gmall.service.UmsMemberReceiveAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>
 * 会员收货地址表 服务实现类
 * </p>
 *
 * @author jin
 * @since 2020-09-21
 */
@Service
public class UmsMemberReceiveAddressServiceImpl extends ServiceImpl<UmsMemberReceiveAddressMapper, UmsMemberReceiveAddress> implements UmsMemberReceiveAddressService {
    @Autowired(required = false)
    UmsMemberReceiveAddressMapper umsMemberReceiveAddressMapper;
    @Override
    public List<UmsMemberReceiveAddress> selectByMemberId(Long memberId) {
        return umsMemberReceiveAddressMapper.selectList(new QueryWrapper<UmsMemberReceiveAddress>().eq("member_id",memberId));
    }
}
