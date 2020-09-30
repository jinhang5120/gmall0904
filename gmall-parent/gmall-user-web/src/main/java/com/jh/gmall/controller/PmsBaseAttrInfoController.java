package com.jh.gmall.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.jh.gmall.entity.PmsBaseAttrInfo;
import com.jh.gmall.entity.PmsBaseAttrValue;
import com.jh.gmall.service.PmsBaseAttrInfoService;
import com.jh.gmall.service.PmsBaseAttrValueService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>
 * 属性表 前端控制器
 * </p>
 *
 * @author jin
 * @since 2020-09-21
 */
@Controller
@CrossOrigin
public class PmsBaseAttrInfoController {
    @Reference
    PmsBaseAttrInfoService pmsBaseAttrInfoService;
    @Reference
    PmsBaseAttrValueService pmsBaseAttrValueService;
    @RequestMapping("/attrInfoList")//前端请求：http://127.0.0.1:8081/attrInfoList?catalog3Id=1
    @ResponseBody
    public List<PmsBaseAttrInfo> attrInfoList(int catalog3Id){
        return pmsBaseAttrInfoService.attrInfoList(catalog3Id);
    }
    @RequestMapping("/saveAttrInfo")//前端请求http://127.0.0.1:8081/saveAttrInfo
    @ResponseBody
    public void saveAttrInfo(@RequestBody PmsBaseAttrInfo pmsBaseAttrInfo){
        System.out.println("pmsBaseAttrInfo = " + pmsBaseAttrInfo);

        if(pmsBaseAttrInfo.getId() == null){
            PmsBaseAttrInfo insertAttrInfo = pmsBaseAttrInfoService.createAttrInfo(pmsBaseAttrInfo);
            System.out.println("result1 = " + insertAttrInfo);

            //注意传参的格式，因为PmsBaseAttrInfo的List<PmsBaseAttrValue>是transient的，rpc调用不能序列化，所以必须要用其他实体类
            int result2 = pmsBaseAttrValueService.createAttrValue(pmsBaseAttrInfo.getAttrValueList(),insertAttrInfo.getId());
            System.out.println("result2 = " + result2);
        }else{
            int result3 = pmsBaseAttrValueService.createAttrValue(pmsBaseAttrInfo.getAttrValueList(),pmsBaseAttrInfo.getId());
            System.out.println("result3 = " + result3);
        }
    }
}

