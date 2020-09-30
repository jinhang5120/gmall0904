package com.example.gmall0917.ums_member.service.impl;

import com.example.gmall0917.ums_member.entity.UmsMemberReceiveAddress;
import com.example.gmall0917.ums_member.mapper.UmsMemberReceiveAddressMapper;
import com.example.gmall0917.ums_member.service.UmsMemberReceiveAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员收货地址表 服务实现类
 * </p>
 *
 * @author jin
 * @since 2020-09-17
 */
@Service
public class UmsMemberReceiveAddressServiceImpl extends ServiceImpl<UmsMemberReceiveAddressMapper, UmsMemberReceiveAddress> implements UmsMemberReceiveAddressService {
    @Autowired(required = false)
    UmsMemberReceiveAddressMapper umsMemberReceiveAddressMapper;
    public int insert(String str){
        UmsMemberReceiveAddress umsMemberReceiveAddress = new UmsMemberReceiveAddress();
        umsMemberReceiveAddress.setName(str);
        return umsMemberReceiveAddressMapper.insert(umsMemberReceiveAddress);
    }
}
