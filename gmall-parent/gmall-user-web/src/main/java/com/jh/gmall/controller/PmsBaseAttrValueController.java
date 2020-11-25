package com.jh.gmall.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.jh.gmall.entity.PmsBaseAttrValue;
import com.jh.gmall.service.PmsBaseAttrValueService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>
 * 属性值表 前端控制器
 * </p>
 *
 * @author jin
 * @since 2020-09-21
 */
@Controller
@CrossOrigin //不要忘记加跨域注解
public class PmsBaseAttrValueController {
    @Reference
    PmsBaseAttrValueService pmsBaseAttrValueService;
    @RequestMapping("/getAttrValueList") //前端请求地址：http://127.0.0.1:8081/getAttrValueList?attrId=12
    @ResponseBody
    public List<PmsBaseAttrValue> getAttrValueList(Long attrId){
        return pmsBaseAttrValueService.getAttrValueList(attrId);
    }
}

