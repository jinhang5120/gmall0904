package com.jh.gmall.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jh.gmall.entity.OmsCartItem;
import com.jh.gmall.mapper.OmsCartItemMapper;
import com.jh.gmall.service.OmsCartItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 购物车表 服务实现类
 * </p>
 *
 * @author jin
 * @since 2020-09-21
 */
@Service
public class OmsCartItemServiceImpl extends ServiceImpl<OmsCartItemMapper, OmsCartItem> implements OmsCartItemService {
    @Autowired(required = false)
    OmsCartItemMapper omsCartItemMapper;
    @Override
    public void addCartItem(OmsCartItem omsCartItem) {
        omsCartItemMapper.insert(omsCartItem);
    }

    @Override
    public void updateCart(OmsCartItem omsCartItemFromDb) {
        omsCartItemMapper.updateById(omsCartItemFromDb);
    }

    @Autowired
    JedisPool jedisPool;
    @Override
    public void syncCache(Long memberId) {
        Jedis jedis = jedisPool.getResource();
        List<OmsCartItem> omsCartItems = omsCartItemMapper.selectList(new QueryWrapper<OmsCartItem>().eq("member_id", memberId));
        HashMap<String, String> map = new HashMap<>();
        for (OmsCartItem omsCartItem : omsCartItems) {
            map.put(omsCartItem.getProductSkuId().toString(),JSON.toJSONString(omsCartItem));//以productSkuId为hash进行存储
        }
        jedis.del("user:"+memberId+":cart");//先删除再增加
        jedis.hmset("user:"+memberId+":cart", map);//注意命名规范
        jedis.close();
    }

    @Override
    public OmsCartItem selectOneFromCart(Long memberId, Long skuId) {
        return omsCartItemMapper.selectOne(new QueryWrapper<OmsCartItem>().eq("product_sku_id", skuId).eq("member_id", memberId));
    }

    @Override
    public List<OmsCartItem> selectListByMemberId(long memberId) {
        return omsCartItemMapper.selectList(new QueryWrapper<OmsCartItem>().eq("member_id",memberId));
    }

    @Override
    public void updateIsCheckedByProductSkuId(Long productSkuId, Integer isChecked) {
//        OmsCartItem omsCartItem = new OmsCartItem();
//        omsCartItem.setProductSkuId(productSkuId);
//        omsCartItem.setIsChecked(isChecked);
        int update = omsCartItemMapper.update(null,
                new UpdateWrapper<OmsCartItem>().eq("product_sku_id", productSkuId).set("is_checked", isChecked));
        System.out.println("update = " + update);
    }
}
