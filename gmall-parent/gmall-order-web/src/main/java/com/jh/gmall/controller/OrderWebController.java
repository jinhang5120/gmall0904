package com.jh.gmall.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jh.gmall.annotation.LoginRequired;
import com.jh.gmall.entity.*;
import com.jh.gmall.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@CrossOrigin
public class OrderWebController {
    @Reference
    UmsMemberReceiveAddressService umsMemberReceiveAddressService;
    @Reference
    OmsCartItemService omsCartItemService;
    @Reference
    OmsOrderService omsOrderService;
    @Reference
    PmsSkuInfoService pmsSkuInfoService;
    @RequestMapping("toTrade")
    @LoginRequired(loginSuccess = true)  //需要被自定义拦截器进行拦截,并且必须处于登录状态
    public String m4(HttpServletRequest request, ModelMap modelMap){
        Long memberId = Long.parseLong(String.valueOf(request.getAttribute("memberId")));
        String nickname = (String)request.getAttribute("nickname");
        System.out.println("memberId = " + memberId);
        System.out.println("nickname = " + nickname);

        List<UmsMemberReceiveAddress> umsMemberReceiveAddresses = umsMemberReceiveAddressService.selectByMemberId(memberId);
        List<OmsCartItem> omsCartItems = omsCartItemService.selectListByMemberId(memberId);
        List<OmsOrderItem> omsOrderItems = new ArrayList<>();
        for (OmsCartItem omsCartItem : omsCartItems) {
            if (omsCartItem.getIsChecked().equals(1)) {//选中状态
                OmsOrderItem omsOrderItem = new OmsOrderItem();
                omsOrderItem.setProductName(omsCartItem.getProductName());
                omsOrderItem.setProductPic(omsCartItem.getProductPic());
                omsOrderItem.setProductPrice(omsCartItem.getPrice());
                omsOrderItem.setProductQuantity(omsCartItem.getQuantity());
                omsOrderItem.setProductId(omsCartItem.getProductId());
                omsOrderItem.setProductSkuId(omsCartItem.getProductSkuId());
                omsOrderItem.setProductSkuCode(omsCartItem.getProductSkuCode());
                omsOrderItem.setProductCategoryId(omsCartItem.getProductCategoryId());

                omsOrderItems.add(omsOrderItem);
            }
        }
        modelMap.put("userAddressList",umsMemberReceiveAddresses);
        modelMap.put("omsOrderItems",omsOrderItems);
        modelMap.put("totalAmount",sumprice(omsOrderItems));

        String tradeCode = omsOrderService.getTradeCode(memberId);
        modelMap.put("tradeCode",tradeCode);
        modelMap.put("memberId",memberId);
        modelMap.put("memberUsername",nickname);
        return "trade";
    }
    private BigDecimal sumprice(List<OmsOrderItem> omsOrderItems){
        BigDecimal bigDecimal = new BigDecimal("0");
        for (OmsOrderItem omsOrderItem : omsOrderItems) {
                bigDecimal = bigDecimal.add(omsOrderItem.getProductPrice().multiply(BigDecimal.valueOf(omsOrderItem.getProductQuantity())));
        }
        return bigDecimal;
    }

    @RequestMapping("submitOrder")
    public ModelAndView submitOrder(OmsOrder omsOrder, ModelMap modelMap){
        String checkResult = omsOrderService.checkTradeCode(omsOrder.getMemberId(),omsOrder.getTradeCode());//验证订单是否重复提交
        System.out.println("checkResult = " + checkResult);
        if(checkResult.equals("fail")){
            return new ModelAndView("tradeFail");
        }
        //根据用户id获得垢面得商品列表和总价格
        /*从前端传回数据*/
        //设置外部订单号，用来和其他系统进行交互，防止重复
        String orderSn = "gmall"+System.currentTimeMillis();
        omsOrder.setOrderSn(orderSn);
        omsOrder.setStatus(0);
        List<OmsOrderItem> omsOrderItems = omsOrder.getOmsOrderItems();
        for (OmsOrderItem omsOrderItem : omsOrderItems) {
            //校验价格和库存数量，远程调用库存系统
            boolean b = pmsSkuInfoService.checkPrice(omsOrderItem.getProductSkuId(),omsOrderItem.getProductPrice());
            if(b){
                omsOrderItem.setOrderSn(orderSn);
            }else{
                return new ModelAndView("tradeFail");
            }
        }
        omsOrder.setOmsOrderItems(omsOrderItems);

        //将订单和订单详情写入数据库
        omsOrder = omsOrderService.saveOrder(omsOrder, omsOrder.getOmsOrderItems());//之所以传两个参数，是因为Dubbo调用不能应用于transient字段
//删除购物车的对应商品

        //重定向支持系统
        ModelAndView mav = new ModelAndView("redirect:http://localhost:8089/index?orderId="+omsOrder.getId());
//        mav.addObject("orderSn",orderSn);
//        mav.addObject("totalAmount",omsOrder.getTotalAmount());
        return mav;
    }
}
