package com.jh.gmall.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jh.gmall.entity.PmsBaseSaleAttr;
import com.jh.gmall.mapper.PmsBaseSaleAttrMapper;
import com.jh.gmall.service.PmsBaseSaleAttrService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

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
public class PmsBaseSaleAttrServiceImpl extends ServiceImpl<PmsBaseSaleAttrMapper, PmsBaseSaleAttr> implements PmsBaseSaleAttrService {
    @Autowired
    PmsBaseSaleAttrMapper pmsBaseSaleAttrMapper;
    @Override
    public List<PmsBaseSaleAttr> baseSaleAttrList() {
        return pmsBaseSaleAttrMapper.selectList(null);
    }

    @Override
    public String selectSaleAttrName(Long saleAttrId) {
        return pmsBaseSaleAttrMapper.selectOne(new QueryWrapper<PmsBaseSaleAttr>().eq("id",saleAttrId)).getName();
    }
}
