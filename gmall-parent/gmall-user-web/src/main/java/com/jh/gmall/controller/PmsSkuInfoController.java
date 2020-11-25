package com.jh.gmall.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.jh.gmall.entity.*;
import com.jh.gmall.service.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>
 * 库存单元表 前端控制器
 * </p>
 *
 * @author jin
 * @since 2020-09-21
 */
@Controller
@CrossOrigin
public class PmsSkuInfoController {
    @Reference
    PmsSkuInfoService pmsSkuInfoService;
    @Reference
    PmsSkuAttrValueService pmsSkuAttrValueService;
    @Reference
    PmsBaseSaleAttrService pmsBaseSaleAttrService;
    @Reference
    PmsSkuSaleAttrValueService pmsSkuSaleAttrValueService;
    @Reference
    PmsSkuImageService pmsSkuImageService;

    @ResponseBody
    @RequestMapping("saveSkuInfo")//前端请求：http://127.0.0.1:8081/saveSkuInfo
    public void saveSkuInfo(@RequestBody PmsSkuInfo pmsSkuInfo) {
        System.out.println("pmsSkuInfo = " + pmsSkuInfo);
        Long skuId = pmsSkuInfoService.saveSkuInfo(pmsSkuInfo);
        System.out.println("pmsSkuInfoService = " + pmsSkuInfoService);
        List<PmsSkuAttrValue> skuAttrValueList = pmsSkuInfo.getSkuAttrValueList();
        for (PmsSkuAttrValue pmsSkuAttrValue : skuAttrValueList) {
            pmsSkuAttrValue.setSkuId(skuId);
            pmsSkuAttrValueService.insertPmsSkuAttrValue(pmsSkuAttrValue);
        }
        List<PmsSkuSaleAttrValue> skuSaleAttrValueList = pmsSkuInfo.getSkuSaleAttrValueList();
        for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : skuSaleAttrValueList) {
            pmsSkuSaleAttrValue.setSkuId(skuId);
            pmsSkuSaleAttrValueService.insertSkuSaleAttrValue(pmsSkuSaleAttrValue);
        }
        List<PmsSkuImage> skuImageList = pmsSkuInfo.getSkuImageList();
        for (PmsSkuImage pmsSkuImage : skuImageList) {
            pmsSkuImage.setSkuId(skuId);
            pmsSkuImageService.insertSkuImage(pmsSkuImage);
        }
    }
}

