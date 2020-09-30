package com.jh.gmall.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.jh.gmall.entity.UmsMember;
import com.jh.gmall.service.UmsMemberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author jin
 * @since 2020-09-21
 */
@RestController
@RequestMapping("/umsMember")
public class UmsMemberController {
    @Reference
    private UmsMemberService umsMemberService;
    @GetMapping("/m1/{id}")
    public UmsMember m1(@PathVariable("id") int id){
        System.out.println("umsMemberService = " + umsMemberService);
        return umsMemberService.add(id);
    }
}

