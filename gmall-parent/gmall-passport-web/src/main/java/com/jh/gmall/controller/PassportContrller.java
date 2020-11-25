package com.jh.gmall.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.jh.gmall.entity.UmsMember;
import com.jh.gmall.service.UmsMemberService;
import com.jh.gmall.util.HttpClientUtil;
import com.jh.gmall.util.JwtUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PassportContrller {
    @Reference
    UmsMemberService umsMemberService;
    @RequestMapping("index")
    public String m1(String returnUrl, ModelMap modelMap){
        modelMap.put("returnUrl",returnUrl);
        return "index";
    }

    @RequestMapping("login")//处理异步请求
    @ResponseBody
    public String m2(UmsMember umsMember, HttpServletRequest request){
        String token = "";
        UmsMember umsMemberLogin = umsMemberService.login(umsMember);
        if(umsMemberLogin!=null){//登录成功
            //制作token
            HashMap<String, Object> map = new HashMap<>();
            map.put("memberId",umsMemberLogin.getId());
            map.put("nickname",umsMemberLogin.getNickname());
            System.out.println("request.getLocalAddr() = " + request.getLocalAddr());
            System.out.println("request.getRemoteAddr() = " + request.getRemoteAddr());
            token = JwtUtil.encode("jinhang", map, request.getRemoteAddr());//实际中参数也要加密
            // 将token存入redis,做备案，调用业务层接口
            umsMemberService.addUserToken(token,umsMemberLogin.getId());
        }else{//登录失败
            token = "fail";
        }
        return token;
    }

    @RequestMapping("verify")
    @ResponseBody
    public String verify(String token, String currentIp){
        //进行jwt校验
        HashMap<String, Object> map = new HashMap<>();
        Map<String, Object> decode = JwtUtil.decode(token, "jinhang", currentIp);
        if (decode!=null) {
            map.put("status","success");
            map.put("memberId",decode.get("memberId"));
            map.put("nickname",decode.get("nickname"));
        }else{
            map.put("status","fail");
        }
        return JSON.toJSONString(map);
    }

    @RequestMapping("vlogin")//处理社交登录
    public String vlogin(String code,HttpServletRequest request){
        //授权码换取access_token
        Map<String,String> paraMap = new HashMap<>();
        paraMap.put("client_id","519870850");
        paraMap.put("client_secret","4214768448c933b853ad46c685ea69cb");
        paraMap.put("grant_type","authorization_code");
        paraMap.put("redirect_uri","http://passport.gmall.com:8086/vlogin");
        paraMap.put("code",code);
        String tmp = HttpClientUtil.doPost("https://api.weibo.com/oauth2/access_token", paraMap);//必须是post请求
        System.out.println("tmp = " + tmp);
        Map map = JSON.parseObject(tmp, Map.class);
        System.out.println("map = " + map);
        //access_token换取用户信息
        String s4 = "https://api.weibo.com/2/users/show.json?access_token="+map.get("access_token")+"&uid="+map.get("uid");
        System.out.println("s4 = " + s4);
        String s1 = HttpClientUtil.doGet(s4);
        Map map1 = JSON.parseObject(s1, Map.class);
        System.out.println("map1 = " + map1);
        //用户信息存入数据库，用户类型设置为微博用户
        /*调用UmsMemberService将map1中的用户信息存入数据库中*/
        //生成jwt的token，并且重定向到首页，携带该token
        /*用上面两个相同的方法生成token，写入cookie中*/
        return "redirect:http://localhost:8084/index";
    }
}
