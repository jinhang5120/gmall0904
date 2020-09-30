package com.jh.gmall.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.jh.gmall.entity.PmsBaseCatalog2;
import com.jh.gmall.entity.PmsBaseCatalog3;
import com.jh.gmall.service.PmsBaseCatalog2Service;
import com.jh.gmall.service.PmsBaseCatalog3Service;
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
public class PmsBaseCatalog3Controller {//前端请求地址：http://127.0.0.1:8081/getCatalog3?catalog2Id=6
    @Reference
    PmsBaseCatalog3Service pmsBaseCatalog3Service;
    @RequestMapping("/getCatalog3")
    @ResponseBody
    public List<PmsBaseCatalog3> getCatalog3(int catalog2Id){
        return pmsBaseCatalog3Service.getCatalog3(catalog2Id);
    }
}
