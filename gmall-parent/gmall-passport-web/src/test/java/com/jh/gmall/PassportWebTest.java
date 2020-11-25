package com.jh.gmall;

import com.alibaba.fastjson.JSON;
import com.jh.gmall.util.HttpClientUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class PassportWebTest {
    @Test
    public void test01(){
//        String s1 = "https://api.weibo.com/oauth2/authorize?client_id=519870850&response_type=code&redirect_uri=http://passport.gmall.com:8086/vlogin";
//        String ss2 = HttpClientUtil.doGet(s1);
//        System.out.println("s2 = " + ss2);
//                String s2 = "http://passport.gmall.com:8086/vlogin?code=24eb5ae6451fc9d4f7d90a5c0460a479";

//        String s3 = "https://api.weibo.com/oauth2/access_token?client_id=519870850&client_secret=4214768448c933b853ad46c685ea69cb&grant_type=authorization_code&redirect_uri=http://passport.gmall.com:8086/vlogin&code=CODE";
        Map<String,String> paraMap = new HashMap<>();
        paraMap.put("client_id","519870850");
        paraMap.put("client_secret","4214768448c933b853ad46c685ea69cb");
        paraMap.put("grant_type","authorization_code");
        paraMap.put("redirect_uri","http://passport.gmall.com:8086/vlogin");
        paraMap.put("code","3ee61d8530c15e7648df3dd25fefea41");
        String s = HttpClientUtil.doPost("https://api.weibo.com/oauth2/access_token", paraMap);//必须是post请求
        System.out.println("s = " + s);
        Map map = JSON.parseObject(s, Map.class);
        System.out.println("map = " + map);
        String s4 = "https://api.weibo.com/2/users/show.json?access_token=2.0045F2yB0iJ1LZf2b63cb2938OeOrB&uid=1807738611";
        String s1 = HttpClientUtil.doGet(s4);
        Map map1 = JSON.parseObject(s1, Map.class);
        System.out.println("map1 = " + map1);
    }

}
