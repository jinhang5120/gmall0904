package com.example.gmall0917.ums_member.controller;


import com.example.gmall0917.ums_member.service.impl.UmsMemberReceiveAddressServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 会员收货地址表 前端控制器
 * </p>
 *
 * @author jin
 * @since 2020-09-17
 */
@RestController
@RequestMapping("/ums_member/ums-member-receive-address")
public class UmsMemberReceiveAddressController {
    @Autowired
    UmsMemberReceiveAddressServiceImpl umsMemberReceiveAddressServiceImpl;
    @RequestMapping("insert/{username}")
    public int insert(String str){
        return umsMemberReceiveAddressServiceImpl.insert(str);
    }
}

