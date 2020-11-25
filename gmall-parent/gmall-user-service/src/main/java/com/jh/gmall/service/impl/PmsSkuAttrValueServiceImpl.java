package com.jh.gmall.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jh.gmall.entity.PmsSkuAttrValue;
import com.jh.gmall.mapper.PmsSkuAttrValueMapper;
import com.jh.gmall.service.PmsSkuAttrValueService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>
 * sku平台属性值关联表 服务实现类
 * </p>
 *
 * @author jin
 * @since 2020-09-21
 */
@Service
public class PmsSkuAttrValueServiceImpl extends ServiceImpl<PmsSkuAttrValueMapper, PmsSkuAttrValue> implements PmsSkuAttrValueService {
    @Autowired
    PmsSkuAttrValueMapper pmsSkuAttrValueMapper;
    @Override
    public void insertPmsSkuAttrValue(PmsSkuAttrValue pmsSkuAttrValue) {
        System.out.println("pmsSkuAttrValueMapper.insert(pmsSkuAttrValue) = " + pmsSkuAttrValueMapper.insert(pmsSkuAttrValue));
    }

    @Override
    public List<PmsSkuAttrValue> selectBySkuId(Long id) {
        return pmsSkuAttrValueMapper.selectList(new QueryWrapper<PmsSkuAttrValue>().eq("sku_id",id));
    }
}
