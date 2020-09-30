package com.example.gmall0917.ums_member.controller;


import com.example.gmall0917.ums_member.entity.UmsMember;
import com.example.gmall0917.ums_member.service.impl.UmsMemberServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author jin
 * @since 2020-09-17
 */
@RestController
@RequestMapping("/ums_member")
public class UmsMemberController {
    @Autowired
    UmsMemberServiceImpl umsMemberServiceimpl;
    @RequestMapping("selectAll")
    public List<UmsMember> selectAll(){
        return umsMemberServiceimpl.selectList();
    }
    @RequestMapping("selectByid")
    public UmsMember selectById(int id){
        return umsMemberServiceimpl.selectById(id);
    }
    @RequestMapping("selectByNameLike/{str}")
    public UmsMember selectByNameLike(@PathVariable  String str){
        return umsMemberServiceimpl.selectByNameLike(str);
    }
    @RequestMapping("insert/{username}")
    public int insert(@PathVariable String username){
        return umsMemberServiceimpl.insert(username);
    }
}

