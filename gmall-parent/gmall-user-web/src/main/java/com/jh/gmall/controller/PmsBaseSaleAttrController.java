package com.jh.gmall.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.jh.gmall.entity.PmsBaseSaleAttr;
import com.jh.gmall.service.PmsBaseSaleAttrService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jin
 * @since 2020-09-21
 */
@Controller
@CrossOrigin
public class PmsBaseSaleAttrController {
    @Reference
    PmsBaseSaleAttrService  pmsBaseSaleAttrService;
    @RequestMapping("baseSaleAttrList")//前端请求地址：http://127.0.0.1:8081/baseSaleAttrList
    @ResponseBody
    public List<PmsBaseSaleAttr> baseSaleAttrList(){
        return pmsBaseSaleAttrService.baseSaleAttrList();
    }
}

