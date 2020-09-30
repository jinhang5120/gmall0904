package com.jh.gmall.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.jh.gmall.entity.PmsProductImage;
import com.jh.gmall.entity.PmsProductInfo;
import com.jh.gmall.entity.PmsProductSaleAttr;
import com.jh.gmall.entity.PmsProductSaleAttrValue;
import com.jh.gmall.service.PmsProductImageService;
import com.jh.gmall.service.PmsProductInfoService;
import com.jh.gmall.service.PmsProductSaleAttrService;
import com.jh.gmall.service.PmsProductSaleAttrValueService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
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
public class PmsProductInfoController {
    @Reference
    PmsProductInfoService pmsProductInfoService;
    @Reference
    PmsProductSaleAttrService pmsProductSaleAttrService;
    @Reference
    PmsProductImageService pmsProductImageService;
    @Reference
    PmsProductSaleAttrValueService pmsProductSaleAttrValueService;
    @RequestMapping("spuList")//前端请求：http://127.0.0.1:8081/spuList?catalog3Id=61
    @ResponseBody
    public List<PmsProductInfo> spuList(int catalog3Id){
        return pmsProductInfoService.spuList(catalog3Id);
    }
    @RequestMapping("saveSpuInfo")//前端请求：http://127.0.0.1:8081/saveSpuInfo
    @ResponseBody
    public void saveSpuInfo(@RequestBody PmsProductInfo pmsProductInfo){
        System.out.println("pmsProductInfo = " + pmsProductInfo);

        PmsProductInfo creatPmsProductInfo = pmsProductInfoService.saveSpuInfo(pmsProductInfo);
        System.out.println("creatPmsProductInfo = " + creatPmsProductInfo);

        for (PmsProductSaleAttr pmsProductSaleAttr : pmsProductInfo.getSpuSaleAttrList()) {
            pmsProductSaleAttr.setProductId(creatPmsProductInfo.getId());
            PmsProductSaleAttr createPmsProduct = pmsProductSaleAttrService.saveProductSaleAttr(pmsProductSaleAttr);
            System.out.println("createPmsProduct = " + createPmsProduct);

            for (PmsProductSaleAttrValue pmsProductSaleAttrValue : pmsProductSaleAttr.getSpuSaleAttrValueList()) {
                pmsProductSaleAttrValue.setProductId(creatPmsProductInfo.getId());
                pmsProductSaleAttrValueService.saveProductSaleAttrValue(pmsProductSaleAttrValue);
            }
        }
        for (PmsProductImage pmsProductImage : pmsProductInfo.getSpuImageList()) {
            pmsProductImage.setProductId(creatPmsProductInfo.getId());
            pmsProductImageService.saveProductImage(pmsProductImage);
        }
    }
}

