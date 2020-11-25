package com.jh.gmall.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jh.gmall.entity.OmsOrderItem;
import com.jh.gmall.entity.PmsSkuInfo;
import com.jh.gmall.mapper.PmsSkuInfoMapper;
import com.jh.gmall.service.PmsSkuInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * 库存单元表 服务实现类
 * </p>
 *
 * @author jin
 * @since 2020-09-21
 */
@Service
@Slf4j
public class PmsSkuInfoServiceImpl extends ServiceImpl<PmsSkuInfoMapper, PmsSkuInfo> implements PmsSkuInfoService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired(required = false)
    PmsSkuInfoMapper pmsSkuInfoMapper;
    @Override
    public Long saveSkuInfo(PmsSkuInfo pmsSkuInfo) {
        System.out.println("pmsSkuInfoMapper.insert(pmsSkuInfo) = " + pmsSkuInfoMapper.insert(pmsSkuInfo));
        pmsSkuInfo = pmsSkuInfoMapper.selectOne(new QueryWrapper<PmsSkuInfo>().setEntity(pmsSkuInfo));
        return pmsSkuInfo.getId();
    }

    @Autowired
    JedisPool jedisPool;
    @Override
    public PmsSkuInfo selectOne(Long skuId, String remoteAddr) {
        PmsSkuInfo pmsSkuInfo = null;

        Jedis jedis = jedisPool.getResource();
        Boolean exists = jedis.exists("PmsSkuInfo:Id:" + skuId);
        logger.info("exists = " + exists);
        if(exists){
            logger.info(Thread.currentThread().getName()+" : "+remoteAddr+" jedis exists key: "+"PmsSkuInfo:Id:" + skuId);
            String pmsSkuInfoJSON = jedis.get("PmsSkuInfo:Id:" + skuId);
            if(!pmsSkuInfoJSON.equals("")){
                logger.info(Thread.currentThread().getName()+" : "+remoteAddr+" jedis parseObject from key: "+"PmsSkuInfo:Id:" + skuId);
                pmsSkuInfo = JSON.parseObject(jedis.get("PmsSkuInfo:Id:" + skuId),PmsSkuInfo.class);
            }
        }else{
            logger.info(Thread.currentThread().getName()+" : "+remoteAddr+" jedis not exists key: "+"PmsSkuInfo:Id:" + skuId);
            //为防止缓存击穿，设置redis自带的分布式锁nx，高并发下只有一个线程能设置成功并返回"OK"
            String token = UUID.randomUUID().toString();//防止锁提前过期，而导致误删除，提供锁的校验机制
            String nx = jedis.set("PmsSkuInfo:Id:" + skuId + "lock", token, "nx", "px", 60000);
            logger.info("nx = " + nx);
            if(nx != null&&nx.equals("OK")){//分布式锁设置成功，可以放行查询数据库
                logger.info(Thread.currentThread().getName()+" : "+remoteAddr+" jedis set lock for 60000");
                pmsSkuInfo = pmsSkuInfoMapper.selectOne(new QueryWrapper<PmsSkuInfo>().eq("id", skuId));
                try {Thread.sleep(5000);} catch (InterruptedException e) {e.printStackTrace();}
                if(pmsSkuInfo==null){
                    jedis.setex("PmsSkuInfo:Id:" + skuId,60,"");//防止缓存穿透,redis中存储值为空的数据
                }else{
                    jedis.set("PmsSkuInfo:Id:" + skuId, JSON.toJSONString(pmsSkuInfo));//数据库查完就存到redis中
                }
                //成功访问数据库并在redis中插入缓存数据就释放锁,防止其他线程仍在等待
                String lockValue = jedis.get("PmsSkuInfo:Id:" + skuId + "lock");
                if(lockValue!=null&&lockValue.equals(token)){//防止锁误删除其他线程获取到的锁，最好此处结合lua脚本，在查询到的同时就删除（防止校验token后锁突然过期又导致删除其他线程的锁）
                    logger.info("jedis.del(\"PmsSkuInfo:Id:\" + skuId + \"lock\") = " + jedis.del("PmsSkuInfo:Id:" + skuId + "lock"));
                }
            }else{//获取分布式锁不成功，等待自旋重新发起查询，防止缓存击穿
                logger.info(Thread.currentThread().getName()+" : "+remoteAddr+" jedis wait for lock");
                try {Thread.sleep(3000);} catch (InterruptedException e) {e.printStackTrace();}
                return selectOne(skuId, remoteAddr);
            }
        }
        return pmsSkuInfo;
    }

    @Override
    public List<PmsSkuInfo> selectListByProductId(Long productId) {
        return pmsSkuInfoMapper.selectList(new QueryWrapper<PmsSkuInfo>().eq("product_id",productId));
    }

    @Override
    public List<PmsSkuInfo> selectAll(){
        return pmsSkuInfoMapper.selectList(null);
    }

    @Override
    public boolean checkPrice(Long productSkuId, BigDecimal productPrice) {
        PmsSkuInfo pmsSkuInfo = pmsSkuInfoMapper.selectById(productSkuId);
        BigDecimal price = BigDecimal.valueOf(pmsSkuInfo.getPrice());
        return price.compareTo(productPrice) == 0;
    }
}
