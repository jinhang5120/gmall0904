package com.jh.gmall.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.jh.gmall.annotation.LoginRequired;
import com.jh.gmall.entity.OmsCartItem;
import com.jh.gmall.entity.PmsSkuInfo;
import com.jh.gmall.service.OmsCartItemService;
import com.jh.gmall.service.PmsSkuInfoService;
import com.jh.gmall.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@CrossOrigin
public class CartWebController {
    @Reference
    private PmsSkuInfoService pmsSkuInfoService;
    @Reference
    private OmsCartItemService omsCartItemService;
    @Autowired
    JedisPool jedisPool;
    @RequestMapping("addToCart")
    @LoginRequired(loginSuccess = false)  //需要被自定义拦截器进行拦截,但并不一定要处于登录状态
    public String m1(Long skuId, Integer quantity, HttpServletRequest request, HttpServletResponse response){
        //查询商品信息
        PmsSkuInfo pmsSkuInfo = pmsSkuInfoService.selectOne(skuId, "");

        OmsCartItem omsCartItem = new OmsCartItem();
        omsCartItem.setCreateDate(new Date());
        omsCartItem.setPrice(BigDecimal.valueOf(pmsSkuInfo.getPrice()));
        omsCartItem.setProductCategoryId(pmsSkuInfo.getCatalog3Id());
        omsCartItem.setProductName(pmsSkuInfo.getSkuName());
        omsCartItem.setProductPic(pmsSkuInfo.getSkuDefaultImg());
        omsCartItem.setProductSkuCode("1111111");
        omsCartItem.setProductSkuId(skuId);
        omsCartItem.setQuantity(quantity);
        omsCartItem.setDeleteStatus(0);
        omsCartItem.setModifyDate(new Date());

        Long memberId = 1l;

        if(memberId==null){//用户没有登录
            List<OmsCartItem> omsCartItems = null;
            //Cookie可能已存在
            String cartListCookieName = CookieUtil.getCookieValue(request, "cartListCookieName", true);
            if(cartListCookieName==null||cartListCookieName.length()==0){//判断cartListCookieName为空
                omsCartItems = new ArrayList<>();
                omsCartItems.add(omsCartItem);
            }else{//判断cartListCookieName不为空
                omsCartItems = JSON.parseArray(cartListCookieName, OmsCartItem.class);
                if(if_cart_exist(omsCartItems,omsCartItem)){
                    for (OmsCartItem cartItem : omsCartItems) {
                        if(cartItem.getProductSkuId().equals(omsCartItem.getProductSkuId())){
                            cartItem.setQuantity(cartItem.getQuantity()+omsCartItem.getQuantity());
                            /*cartItem.setPrice(cartItem.getPrice().add(omsCartItem.getPrice()));*/
                            break;
                        }
                    }
                }else{
                    omsCartItems.add(omsCartItem);
                }
            }
            CookieUtil.setCookie(request,response,"cartListCookieName",
                    JSON.toJSONString(omsCartItems),60*60,true);//过期时间一个小时
        }else{//用户登录，从数据库中查出
            OmsCartItem omsCartItemFromDb = omsCartItemService.selectOneFromCart(memberId,skuId);
            if(omsCartItemFromDb==null){//数据库中该用户没有添加该商品的记录
                omsCartItem.setMemberId(memberId);
                omsCartItemService.addCartItem(omsCartItem);
            }else {//数据库中该用户存在该商品的记录
                omsCartItemFromDb.setQuantity(omsCartItemFromDb.getQuantity()+omsCartItem.getQuantity());
                omsCartItemService.updateCart(omsCartItemFromDb);
            }
            omsCartItemService.syncCache(memberId);
        }
        return "redirect:/success.html";
    }

    private boolean if_cart_exist(List<OmsCartItem> omsCartItems, OmsCartItem omsCartItem) {
        boolean b = false;
        Long productSkuId = omsCartItem.getProductSkuId();
        for (OmsCartItem cartItem : omsCartItems) {
            if(cartItem.getProductSkuId().equals(productSkuId)){
                b = true;
            }
        }
        return b;
    }

    @RequestMapping("cartList")
    @LoginRequired(loginSuccess = false)  //需要被自定义拦截器进行拦截,但并不一定要处于登录状态
    public String m2(HttpServletRequest request, ModelMap modelMap){
        Long memberId = 1l;
        List<OmsCartItem> omsCartItems = null;
        if(memberId==null){//用户没有登录
            String cartListCookieName = CookieUtil.getCookieValue(request, "cartListCookieName", true);
            if(cartListCookieName!=null&&cartListCookieName.length()!=0){
                omsCartItems = JSON.parseArray(cartListCookieName, OmsCartItem.class);
            }
        }else{//用户已登录，从缓存中查询购物车信息
            omsCartItems = searchFromCache(memberId);
        }
        modelMap.put("cartList",omsCartItems);
        modelMap.put("SumPrice",sumprice(omsCartItems));
        return "cartList";
    }
    private BigDecimal sumprice(List<OmsCartItem> omsCartItems){
        BigDecimal bigDecimal = new BigDecimal("0");
        for (OmsCartItem omsCartItem : omsCartItems) {
            if(omsCartItem.getIsChecked().equals(1)){
                bigDecimal = bigDecimal.add(omsCartItem.getTotalPrice());
            }
        }
        return bigDecimal;
    }
    private List<OmsCartItem> searchFromCache(Long memberId){
        List<OmsCartItem> omsCartItems = new ArrayList<>();
        Jedis jedis = jedisPool.getResource();
        List<String> hvals = jedis.hvals("user:" + memberId + ":cart");
        for (String hval : hvals) {
            omsCartItems.add(JSON.parseObject(hval,OmsCartItem.class));
        }
        jedis.close();
        for (OmsCartItem omsCartItem : omsCartItems) {
            omsCartItem.setTotalPrice(omsCartItem.getPrice().multiply(BigDecimal.valueOf(omsCartItem.getQuantity())));
        }
        return omsCartItems;
    }
    @RequestMapping("checkCart")
    @LoginRequired(loginSuccess = true)  //需要被自定义拦截器进行拦截,并且必须处于登录状态
    public String m3(Integer isChecked, Long productSkuId, ModelMap modelMap){
        Long memberId = 1l;
        omsCartItemService.updateIsCheckedByProductSkuId(productSkuId,isChecked);//调用服务修改状态
        omsCartItemService.syncCache(memberId);//同步刷新缓存
        List<OmsCartItem> omsCartItems = searchFromCache(memberId);
        modelMap.put("cartList",searchFromCache(memberId));//读取缓存中数据
        modelMap.put("SumPrice",sumprice(omsCartItems));
        return "cartListInner";
    }
}
