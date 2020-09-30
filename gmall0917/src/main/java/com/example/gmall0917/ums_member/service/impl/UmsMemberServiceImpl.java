package com.example.gmall0917.ums_member.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.gmall0917.ums_member.entity.UmsMember;
import com.example.gmall0917.ums_member.mapper.UmsMemberMapper;
import com.example.gmall0917.ums_member.service.UmsMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author jin
 * @since 2020-09-17
 */
@Service
public class UmsMemberServiceImpl extends ServiceImpl<UmsMemberMapper, UmsMember> implements UmsMemberService {
    @Autowired(required = false)
    UmsMemberMapper umsMemberMapper;
    public List<UmsMember> selectList(){
        return umsMemberMapper.selectList(null);
    }
    public UmsMember selectById(int id){
        return umsMemberMapper.selectById(id);
    }
    public UmsMember selectByNameLike(String str){
        QueryWrapper<UmsMember> wrapper = new QueryWrapper<>();
        wrapper.like("username",str);
        return umsMemberMapper.selectOne(wrapper);
    }
    public int insert(String username){
        UmsMember umsMember = new UmsMember();
        umsMember.setUsername(username);
        return umsMemberMapper.insert(umsMember);
    }
}
