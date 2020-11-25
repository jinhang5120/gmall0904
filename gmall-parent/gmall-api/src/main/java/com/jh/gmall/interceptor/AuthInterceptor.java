package com.jh.gmall.interceptor;

import com.alibaba.fastjson.JSON;
import com.jh.gmall.annotation.LoginRequired;
import com.jh.gmall.util.CookieUtil;
import com.jh.gmall.util.HttpClientUtil;
import javassist.util.proxy.MethodHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Component
public class AuthInterceptor extends HandlerInterceptorAdapter {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler.getClass().isAssignableFrom(HandlerMethod.class)){//因为拦截的是所有请求，可能handler并不是HandlerMethod的实现，所以要先判断
            HandlerMethod hm = (HandlerMethod) handler;
            LoginRequired methodAnnotation = hm.getMethodAnnotation(LoginRequired.class);
            if(methodAnnotation==null){//无LoginRequired的注解，直接放行
                return true;
            }else{//有LoginRequired的注解，需要拦截
                System.out.println("----被拦截-----");

                //解决token取值问题，新老token存在覆盖问题
                String token = "";
                String oldToken = CookieUtil.getCookieValue(request,"oldToken",true);
                if(StringUtils.isNotBlank(oldToken)) token = oldToken;
                String newToken = request.getParameter("token");
                if(StringUtils.isNotBlank(newToken)) token = newToken;

                //验证
                String success = "fail";
                Map<String, String> map = new HashMap<>();
                if(StringUtils.isNotBlank(token)){
                    System.out.println("token = " + token);
                    String successJson = HttpClientUtil.doGet("http://localhost:8086/verify?token=" + token+"&currentIp="+request.getRemoteAddr());
                    map = JSON.parseObject(successJson, Map.class);
                    System.out.println("map = " + map);
                    success = map.get("status");
                }


                boolean loginSuccess = methodAnnotation.loginSuccess();
                if(loginSuccess){//必须登录成功
                    if(success.equals("success")){//验证通过,覆盖cookie中的token，写入token携带的用户信息
                        request.setAttribute("memberId",map.get("memberId"));
                        request.setAttribute("nickname",map.get("nickname"));
                        if (StringUtils.isNotBlank(token)) {
                            CookieUtil.setCookie(request,response,"oldToken",token,60*60,true);
                        }
                    }else{//验证不通过,重定向回passport登录
                        response.sendRedirect("http://localhost:8086/index?returnUrl="+request.getRequestURL());
                        return false;
                    }
                }else{//验证不通过也可以放行，但是仍需要验证
                    if(success.equals("success")){//验证通过,将token携带的用户信息写入
                        request.setAttribute("memberId",map.get("memberId"));
                        request.setAttribute("nickname",map.get("nickname"));
                        if (StringUtils.isNotBlank(token)) {
                            CookieUtil.setCookie(request,response,"oldToken",token,60*60,true);
                        }
                    }
                }
            }
        }
        return true;
    }
}
