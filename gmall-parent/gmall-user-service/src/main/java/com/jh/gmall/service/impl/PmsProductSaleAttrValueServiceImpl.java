package com.jh.gmall.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.jh.gmall.entity.PmsProductSaleAttrValue;
import com.jh.gmall.mapper.PmsProductSaleAttrValueMapper;
import com.jh.gmall.service.PmsProductSaleAttrValueService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

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
}
