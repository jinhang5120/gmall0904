package com.jh.gmall.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jh.gmall.entity.UmsMember;
import com.jh.gmall.mapper.UmsMemberMapper;
import com.jh.gmall.service.UmsMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author jin
 * @since 2020-09-21
 */
@Service
public class UmsMemberServiceImpl extends ServiceImpl<UmsMemberMapper, UmsMember> implements UmsMemberService {
    @Autowired(required = false)
    UmsMemberMapper umsMemberMapper;
    @Override
    public UmsMember add(int i) {
        System.out.println("umsMemberMapper = " + umsMemberMapper);
        System.out.println("umsMemberMapper.selectById(1) = " + umsMemberMapper.selectById(1));
        System.out.println("umsMemberMapper.selectCount(null) = " + umsMemberMapper.selectCount(null));
        return umsMemberMapper.selectById(i);
    }

    @Autowired
    JedisPool jedisPool;
    @Override
    public UmsMember login(UmsMember umsMember) {//为了应对大量用户登录，使用缓存
        Jedis jedis = jedisPool.getResource();
        String umsMemberStr = jedis.get("user:" + umsMember.getPassword() + ":info");
        if(StringUtils.isNotBlank(umsMemberStr)){//从缓存中查
            return JSON.parseObject(umsMemberStr, UmsMember.class);
        }else{//从数据库中查
            UmsMember umsMemberFromDb = umsMemberMapper.selectOne(new QueryWrapper<>(umsMember));
            if(umsMemberFromDb!=null){
                jedis.setex("user:" + umsMember.getPassword() + ":info",60*60,JSON.toJSONString(umsMemberFromDb));
            }
            return umsMemberFromDb;
        }
    }

    @Override
    public void addUserToken(String token, Long id) {
        Jedis jedis = jedisPool.getResource();
        jedis.setex("user:"+token+":token",60*60,token);
        jedis.close();
    }

    @Override
    public UmsMember selectById(Long memberId) {
        return umsMemberMapper.selectById(memberId);
    }
}
