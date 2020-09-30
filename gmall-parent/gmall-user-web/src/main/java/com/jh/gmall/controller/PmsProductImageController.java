package com.jh.gmall.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.jh.gmall.entity.PmsProductImage;
import com.jh.gmall.service.PmsProductImageService;
import org.apache.commons.lang3.StringUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 商品图片表 前端控制器
 * </p>
 *
 * @author jin
 * @since 2020-09-21
 */
@Controller
@CrossOrigin
public class PmsProductImageController {
    @Reference
    PmsProductImageService pmsProductImageService;
    @ResponseBody
    @RequestMapping("fileUpload") //http://127.0.0.1:8081/fileUpload
    public String fileUpload(@RequestParam("file") MultipartFile file) throws IOException, MyException {
        String imgUrl="http://10.27.100.113";
        if(file!=null){
            System.out.println("multipartFile = " + file.getName()+"|"+file.getSize());

            String configFile = this.getClass().getResource("/tracker.conf").getFile();
            ClientGlobal.init(configFile);
            TrackerClient trackerClient=new TrackerClient();
            TrackerServer trackerServer=trackerClient.getTrackerServer();
            StorageClient storageClient=new StorageClient(trackerServer,null);
            String extName = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");

            String[] upload_file = storageClient.upload_file(file.getBytes(), extName, null);
            for (int i = 0; i < upload_file.length; i++) {
                imgUrl+="/"+upload_file[i]; //拼接图片的fastdfs访问url
            }
        }
        System.out.println("imgUrl = " + imgUrl);
        return imgUrl;
    }
    @ResponseBody
    @RequestMapping("spuImageList") //前端请求地址：http://127.0.0.1:8081/spuImageList?spuId=24
    public List<PmsProductImage> spuImageList(int spuId){
        return pmsProductImageService.spuImageList(spuId);
    }
}

