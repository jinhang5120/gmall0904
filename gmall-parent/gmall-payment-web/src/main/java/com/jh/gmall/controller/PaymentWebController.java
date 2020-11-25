package com.jh.gmall.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jh.gmall.annotation.LoginRequired;
import com.jh.gmall.entity.OmsOrder;
import com.jh.gmall.entity.PaymentInfo;
import com.jh.gmall.service.OmsOrderService;
import com.jh.gmall.service.PaymentInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

@Controller
public class PaymentWebController {
    @Reference
    OmsOrderService omsOrderService;
    @RequestMapping("index")
    @LoginRequired(loginSuccess = true)
    public String index(Long orderId, ModelMap modelMap){
        System.out.println("orderId = " + orderId);
        OmsOrder omsOrder = omsOrderService.selectById(orderId);
        modelMap.put("orderId",orderId);
        modelMap.put("nickName",omsOrder.getMemberUsername());
        modelMap.put("totalAmount",omsOrder.getTotalAmount());
        return "index";
    }

    @Reference
    PaymentInfoService paymentInfoService;
    @RequestMapping("alipay/submit")
    @LoginRequired(loginSuccess = true)
    public String alipaySubmit(Long orderId){
        System.out.println("orderId = " + orderId);
        //
        OmsOrder omsOrder = omsOrderService.selectById(orderId);
        PaymentInfo paymentInfo = new PaymentInfo(null, omsOrder.getOrderSn(), omsOrder.getId().toString(), null,
                omsOrder.getTotalAmount(), null, "以付款", new Date(), null, null, null);
        paymentInfoService.insert(paymentInfo);
        //更新用户的支付状态

        //支付成功后，引起系统服务-》订单服务更新-》库存服务-》物流服务
        return "finish";
    }
}
