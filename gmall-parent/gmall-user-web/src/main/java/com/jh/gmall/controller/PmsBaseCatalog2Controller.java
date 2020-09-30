package com.jh.gmall.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.jh.gmall.entity.PmsBaseCatalog2;
import com.jh.gmall.service.PmsBaseCatalog2Service;
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
public class PmsBaseCatalog2Controller {//前端请求格式：http://127.0.0.1:8081/getCatalog2?catalog1Id=15
    @Reference
    PmsBaseCatalog2Service pmsBaseCatalog2Service;
    @RequestMapping("/getCatalog2")
    @ResponseBody
    public List<PmsBaseCatalog2> getCatalog2(int catalog1Id){
        return pmsBaseCatalog2Service.getCatalog2(catalog1Id);
    }
}
