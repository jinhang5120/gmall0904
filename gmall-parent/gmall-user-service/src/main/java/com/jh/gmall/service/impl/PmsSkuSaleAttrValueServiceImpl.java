package com.jh.gmall.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jh.gmall.entity.PmsSkuSaleAttrValue;
import com.jh.gmall.mapper.PmsSkuSaleAttrValueMapper;
import com.jh.gmall.service.PmsSkuSaleAttrValueService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>
 * sku销售属性值 服务实现类
 * </p>
 *
 * @author jin
 * @since 2020-09-21
 */
@Service
public class PmsSkuSaleAttrValueServiceImpl extends ServiceImpl<PmsSkuSaleAttrValueMapper, PmsSkuSaleAttrValue> implements PmsSkuSaleAttrValueService {
    @Autowired
    PmsSkuSaleAttrValueMapper pmsSkuSaleAttrValueMapper;
    @Override
    public void insertSkuSaleAttrValue(PmsSkuSaleAttrValue pmsSkuSaleAttrValue) {
        System.out.println("pmsSkuSaleAttrValueMapper.insert(pmsSkuSaleAttrValue) = " + pmsSkuSaleAttrValueMapper.insert(pmsSkuSaleAttrValue));
    }

    @Override
    public List<PmsSkuSaleAttrValue> selectListBySkuId(Long skuId) {
        return pmsSkuSaleAttrValueMapper.selectList(new QueryWrapper<PmsSkuSaleAttrValue>().eq("sku_id",skuId));
    }
}
