package com.jh.gmall.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jh.gmall.entity.PmsProductSaleAttrValue;
import com.jh.gmall.mapper.PmsProductSaleAttrValueMapper;
import com.jh.gmall.service.PmsProductSaleAttrValueService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>
 * spu销售属性值 服务实现类
 * </p>
 *
 * @author jin
 * @since 2020-09-21
 */
@Service
public class PmsProductSaleAttrValueServiceImpl extends ServiceImpl<PmsProductSaleAttrValueMapper, PmsProductSaleAttrValue> implements PmsProductSaleAttrValueService {
    @Autowired
    PmsProductSaleAttrValueMapper pmsProductSaleAttrValueMapper;
    @Override
    public void saveProductSaleAttrValue(PmsProductSaleAttrValue pmsProductSaleAttrValue) {
        System.out.println("pmsProductSaleAttrValueMapper.insert(pmsProductSaleAttrValue) = " + pmsProductSaleAttrValueMapper.insert(pmsProductSaleAttrValue));
    }

    @Override
    public List<PmsProductSaleAttrValue> getProductSaleAttrValueList(Long spuId, Long saleAttrId) {
        return pmsProductSaleAttrValueMapper.selectList(new QueryWrapper<PmsProductSaleAttrValue>().eq("product_id",spuId).eq("sale_attr_id",saleAttrId));
    }
}
