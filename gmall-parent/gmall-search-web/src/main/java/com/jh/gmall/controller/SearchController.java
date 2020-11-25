package com.jh.gmall.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jh.gmall.annotation.LoginRequired;
import com.jh.gmall.entity.*;
import com.jh.gmall.service.PmsBaseAttrInfoService;
import com.jh.gmall.service.PmsBaseAttrValueService;
import com.jh.gmall.service.SearchService;
import jodd.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

@Controller
@CrossOrigin
public class SearchController {
    @RequestMapping("index")
    @LoginRequired(loginSuccess = false)//进首页进行一次拦截，如果有token就写入
    public String m1(){
        return "index";
    }

    @Reference
    SearchService searchService;
    @Reference
    PmsBaseAttrInfoService pmsBaseAttrInfoService;
    @Reference
    PmsBaseAttrValueService pmsBaseAttrValueService;
    @RequestMapping("list.html")
    public String m2(PmsSearchParam pmsSearchParam, ModelMap modelMap, HttpServletRequest request){
        System.out.println("pmsSearchParam = " + pmsSearchParam);
        List<PmsSearchSkuInfo> skuLsInfoList = searchService.selectList(pmsSearchParam);

        modelMap.put("skuLsInfoList",skuLsInfoList);

        HashSet<Long> attrIdSet = new HashSet<>();
        HashSet<Long> valueIdSet = new HashSet<>();
        for (PmsSearchSkuInfo pmsSearchSkuInfo : skuLsInfoList) {
            List<PmsSkuAttrValue> skuAttrValueList = pmsSearchSkuInfo.getSkuAttrValueList();
            for (PmsSkuAttrValue pmsSkuAttrValue : skuAttrValueList) {
                attrIdSet.add(pmsSkuAttrValue.getAttrId());//从搜索结果中去重获取属性ID
                valueIdSet.add(pmsSkuAttrValue.getValueId());//从搜索结果中去重获取属性值ID
            }
        }

        List<PmsBaseAttrInfo> pmsBaseAttrInfos = pmsBaseAttrInfoService.selectListBySet(attrIdSet);
        for (PmsBaseAttrInfo pmsBaseAttrInfo : pmsBaseAttrInfos) {
            List<PmsBaseAttrValue> attrValueList = pmsBaseAttrValueService.getAttrValueList(pmsBaseAttrInfo.getId());
            attrValueList.removeIf(next -> !valueIdSet.contains(next.getId()));//去除搜索结果中不存在的属性值
            pmsBaseAttrInfo.setAttrValueList(attrValueList);
        }
        modelMap.put("attrList",pmsBaseAttrInfos);

        String urlParam = request.getQueryString();
        System.out.println("urlParam = " + urlParam);
        modelMap.put("urlParam",urlParam);//追加get请求的参数

        modelMap.put("keyword",pmsSearchParam.getKeyword());


        if(pmsSearchParam.getValueId()!=null){//只针对具体属性值增加面包屑功能
            List<PmsSearchCrumb> pmsSearchCrumbs = new ArrayList<>();
            for (Long valueId : pmsSearchParam.getValueId()) {
                PmsBaseAttrValue pmsBaseAttrValue = pmsBaseAttrValueService.getValueNameById(valueId);
                String tmp = "valueId="+valueId;
                pmsSearchCrumbs.add(new PmsSearchCrumb(pmsBaseAttrValue.getValueName(),StringUtil.replace(urlParam,tmp,"")));//更新面包屑的get请求链接（排除自己）
            }
            System.out.println("pmsSearchCrumbs = " + pmsSearchCrumbs);
            modelMap.put("attrValueSelectedList",pmsSearchCrumbs);
        }

        return "list";
    }
}
