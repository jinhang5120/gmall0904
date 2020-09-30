package com.jh.gmall.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.jh.gmall.entity.PmsBaseCatalog1;
import com.jh.gmall.service.PmsBaseCatalog1Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>
 * 一级分类表 前端控制器
 * </p>
 *
 * @author jin
 * @since 2020-09-21
 */
@Controller
@CrossOrigin(origins = "*",maxAge = 3600,methods = {RequestMethod.GET, RequestMethod.POST})
public class PmsBaseCatalog1Controller {
    @Reference
    PmsBaseCatalog1Service pmsBaseCatalog1Service;
    @RequestMapping("/getCatalog1")
    @ResponseBody
    public List<PmsBaseCatalog1> getCatalog1(){
        return pmsBaseCatalog1Service.getCatalog1();
    }
}

