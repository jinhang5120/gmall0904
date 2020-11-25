package com.jh.gmall.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jh.gmall.entity.OmsOrder;
import com.jh.gmall.entity.OmsOrderItem;
import com.jh.gmall.mapper.OmsOrderItemMapper;
import com.jh.gmall.mapper.OmsOrderMapper;
import com.jh.gmall.service.OmsOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author jin
 * @since 2020-09-21
 */
@Service
public class OmsOrderServiceImpl extends ServiceImpl<OmsOrderMapper, OmsOrder> implements OmsOrderService {
    @Autowired
    JedisPool jedisPool;

    @Override
    public String checkTradeCode(Long memberId, String tradeCode) {
        Jedis jedis = null;
        String result = "fail";
        try {
            jedis = jedisPool.getResource();
/*            String checkTradeCode = jedis.get("user:" + memberId + ":tradeCode");
            if(checkTradeCode!=null&&checkTradeCode.equals(tradeCode)){
                jedis.del("user:" + memberId + ":tradeCode");
                result = "success";
            }*/
            String script = "if redis.call('get',KEYS[1])==ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
            //使用lua脚本，在redis中获取key的value值并比较的同时就把该key删掉，防止高并发下的诸多问题，保持了原子性
            Long eval = (Long)jedis.eval(script, Collections.singletonList("user:" + memberId + ":tradeCode"), Collections.singletonList(tradeCode));
            if(eval!=null&&eval!=0){
                result = "success";
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

    @Override
    public String getTradeCode(Long memberId) {
        String tradeCode = UUID.randomUUID().toString();
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.setex("user:"+memberId+":tradeCode",60*60,tradeCode);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return tradeCode;
    }

    @Autowired
    OmsOrderMapper omsOrderMapper;
    @Autowired
    OmsOrderItemMapper omsOrderItemMapper;
    @Override
    public OmsOrder saveOrder(OmsOrder omsOrder, List<OmsOrderItem> omsOrderItems) {
        System.out.println("omsOrder = " + omsOrder);
        int insert = omsOrderMapper.insert(omsOrder);
        System.out.println("insert = " + insert);
        OmsOrder omsOrder1 = omsOrderMapper.selectOne(new QueryWrapper<>(omsOrder));
        System.out.println("omsOrder1 = " + omsOrder1);
        for (OmsOrderItem omsOrderItem : omsOrderItems) {
            omsOrderItem.setOrderId(omsOrder1.getId());
            omsOrderItemMapper.insert(omsOrderItem);
        }
        return omsOrder1;
    }

    @Override
    public OmsOrder selectById(Long orderId) {
        return omsOrderMapper.selectById(orderId);
    }
}
