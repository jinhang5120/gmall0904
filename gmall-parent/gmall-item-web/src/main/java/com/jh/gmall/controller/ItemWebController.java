package com.jh.gmall.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.jh.gmall.entity.*;
import com.jh.gmall.service.*;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class ItemWebController {
    @RequestMapping("index")
    public String m1(ModelMap modelMap){
        modelMap.put("key1","value1");
        return "index";
    }

    @Reference
    PmsSkuInfoService pmsSkuInfoService;
    @Reference
    PmsSkuImageService pmsSkuImageService;
    @Reference
    PmsProductSaleAttrService pmsProductSaleAttrService;
    @Reference
    PmsProductSaleAttrValueService pmsProductSaleAttrValueService;
    @Reference
    PmsSkuSaleAttrValueService pmsSkuSaleAttrValueService;
    @RequestMapping("{skuId}.html")
    public String m2(@PathVariable("skuId") Long skuId, ModelMap map, HttpServletRequest request){
        PmsSkuInfo pmsSkuInfo = pmsSkuInfoService.selectOne(skuId,request.getRemoteAddr());
        map.put("skuInfo",pmsSkuInfo);

        pmsSkuInfo.setSkuImageList(pmsSkuImageService.selectList(skuId));

        List<PmsSkuSaleAttrValue> pmsSkuSaleAttrValueList = pmsSkuSaleAttrValueService.selectListBySkuId(skuId);

        List<PmsProductSaleAttr> spuSaleAttrListCheckBySku = pmsProductSaleAttrService.selectListByProductId(pmsSkuInfo.getProductId());

        for (PmsProductSaleAttr pmsProductSaleAttr : spuSaleAttrListCheckBySku) {
            List<PmsProductSaleAttrValue> productSaleAttrValueList = pmsProductSaleAttrValueService.getProductSaleAttrValueList(pmsProductSaleAttr.getProductId(), pmsProductSaleAttr.getSaleAttrId());
            for (PmsProductSaleAttrValue pmsProductSaleAttrValue : productSaleAttrValueList) {
                for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : pmsSkuSaleAttrValueList) {
                    if(pmsProductSaleAttrValue.getId().equals(pmsSkuSaleAttrValue.getSaleAttrValueId()) && pmsProductSaleAttrValue.getSaleAttrId().equals(pmsSkuSaleAttrValue.getSaleAttrId())){
                        pmsProductSaleAttrValue.setIsChecked(true);break;
                    }
                }
            }
            pmsProductSaleAttr.setSpuSaleAttrValueList(productSaleAttrValueList);
        }
        map.put("spuSaleAttrListCheckBySku",spuSaleAttrListCheckBySku);

        //建立所有属性值ID作为键值，skuID作为值的Hash表，作为json字符串传到前端，前端jQuery再解析成json对象，通过判断选中的红框属性值组合得到skuId,再刷新页面
        HashMap<String, String> skuSaleAttrValueHashMap = new HashMap<>();
        List<PmsSkuInfo> pmsSkuInfoList = pmsSkuInfoService.selectListByProductId(pmsSkuInfo.getProductId());
        for (PmsSkuInfo skuInfo : pmsSkuInfoList) {
            List<PmsSkuSaleAttrValue> pmsSkuSaleAttrValues = pmsSkuSaleAttrValueService.selectListBySkuId(skuInfo.getId());
            if(pmsSkuSaleAttrValues.size()!=0){
                String key = "";
                for (PmsSkuSaleAttrValue pmsSkuSaleAttrValue : pmsSkuSaleAttrValues) {
                    key+=pmsSkuSaleAttrValue.getSaleAttrValueId();
                    key+="|";
                }
                key = key.substring(0,key.length()-1);
                skuSaleAttrValueHashMap.put(key,skuInfo.getId()+"");
            }
        }
        String s = JSON.toJSONString(skuSaleAttrValueHashMap);//转化为json字符串
        map.put("valuesSku",s);

        return "item";
    }
}
