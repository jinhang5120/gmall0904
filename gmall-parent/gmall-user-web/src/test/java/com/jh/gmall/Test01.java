package com.jh.gmall;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class Test01 {
    @Test
    void m1() throws IOException, MyException {
        System.out.println("\"abc\" = " + "abc");
        String file = this.getClass().getResource("/tracker.conf").getFile();
        System.out.println("file = " + file);
        ClientGlobal.init(file);//fdfs配置文件地址
        TrackerClient trackerClient=new TrackerClient();
        TrackerServer trackerServer=trackerClient.getTrackerServer();
        StorageClient storageClient=new StorageClient(trackerServer,null);
        String orginalFilename="C://Users//jinha//Pictures//a.JPG";
        String[] upload_file = storageClient.upload_file(orginalFilename, "jpg", null);//文件路径，扩展名，文件元数据列表
        for (int i = 0; i < upload_file.length; i++) {
            String s = upload_file[i];
            System.out.println("s = " + s);
        }
    }
}
