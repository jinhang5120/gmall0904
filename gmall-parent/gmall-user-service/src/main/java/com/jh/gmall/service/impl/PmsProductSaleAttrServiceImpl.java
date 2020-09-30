package com.jh.gmall.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jh.gmall.entity.PmsProductSaleAttr;
import com.jh.gmall.entity.PmsProductSaleAttrValue;
import com.jh.gmall.mapper.PmsProductSaleAttrMapper;
import com.jh.gmall.mapper.PmsProductSaleAttrValueMapper;
import com.jh.gmall.service.PmsProductSaleAttrService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jin
 * @since 2020-09-21
 */
@Service
public class PmsProductSaleAttrServiceImpl extends ServiceImpl<PmsProductSaleAttrMapper, PmsProductSaleAttr> implements PmsProductSaleAttrService {
    @Autowired
    PmsProductSaleAttrMapper pmsProductSaleAttrMapper;
    @Autowired
    PmsProductSaleAttrValueMapper pmsProductSaleAttrValueMapper;
    @Override
    public PmsProductSaleAttr saveProductSaleAttr(PmsProductSaleAttr pmsProductSaleAttr) {
        System.out.println("pmsProductSaleAttrMapper.insert(pmsProductSaleAttr) = " + pmsProductSaleAttrMapper.insert(pmsProductSaleAttr));
        return pmsProductSaleAttrMapper.selectOne(new QueryWrapper<PmsProductSaleAttr>().eq("product_id",pmsProductSaleAttr.getProductId()).eq("sale_attr_id",pmsProductSaleAttr.getSaleAttrId()));
    }

    @Override
    public List<PmsProductSaleAttr> spuSaleAttrList(int spuId) {
        List<PmsProductSaleAttr> list = pmsProductSaleAttrMapper.selectList(new QueryWrapper<PmsProductSaleAttr>().eq("product_id", spuId));
        for (PmsProductSaleAttr pmsProductSaleAttr : list) {
            pmsProductSaleAttr.setSpuSaleAttrValueList(pmsProductSaleAttrValueMapper.selectList(new QueryWrapper<PmsProductSaleAttrValue>().eq("product_id",spuId)));
        }
        return list;
    }
}
