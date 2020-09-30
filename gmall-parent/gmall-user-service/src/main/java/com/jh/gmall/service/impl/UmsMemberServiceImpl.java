package com.jh.gmall.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.jh.gmall.entity.UmsMember;
import com.jh.gmall.mapper.UmsMemberMapper;
import com.jh.gmall.service.UmsMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author jin
 * @since 2020-09-21
 */
@Service
public class UmsMemberServiceImpl extends ServiceImpl<UmsMemberMapper, UmsMember> implements UmsMemberService {
    @Autowired(required = false)
    UmsMemberMapper umsMemberMapper;
    @Override
    public UmsMember add(int i) {
        System.out.println("umsMemberMapper = " + umsMemberMapper);
        System.out.println("umsMemberMapper.selectById(1) = " + umsMemberMapper.selectById(1));
        System.out.println("umsMemberMapper.selectCount(null) = " + umsMemberMapper.selectCount(null));
        return umsMemberMapper.selectById(i);
    }
}
