package com.jh.gmall.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.jh.gmall.entity.PmsProductSaleAttr;
import com.jh.gmall.service.PmsProductSaleAttrService;
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
public class PmsProductSaleAttrController {
    @Reference
    PmsProductSaleAttrService pmsProductSaleAttrService;
    @RequestMapping("spuSaleAttrList") //前端请求：http://127.0.0.1:8081/spuSaleAttrList?spuId=24
    @ResponseBody
    public List<PmsProductSaleAttr> spuSaleAttrList(int spuId){
        return pmsProductSaleAttrService.spuSaleAttrList(spuId);
    }
}

