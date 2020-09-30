package com.jh.gmall.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jh.gmall.entity.PmsProductInfo;
import com.jh.gmall.mapper.PmsProductInfoMapper;
import com.jh.gmall.service.PmsProductInfoService;
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
public class PmsProductInfoServiceImpl extends ServiceImpl<PmsProductInfoMapper, PmsProductInfo> implements PmsProductInfoService {
    @Autowired
     PmsProductInfoMapper pmsProductInfoMapper;
    @Override
    public List<PmsProductInfo> spuList(int catalog3Id) {
        return pmsProductInfoMapper.selectList(new QueryWrapper<PmsProductInfo>().eq("catalog3_id",catalog3Id));
    }

    @Override
    public PmsProductInfo saveSpuInfo(PmsProductInfo pmsProductInfo) {
        int insert = pmsProductInfoMapper.insert(pmsProductInfo);
        System.out.println("insert = " + insert);
        return pmsProductInfoMapper.selectOne(new QueryWrapper<PmsProductInfo>().eq("product_name",pmsProductInfo.getProductName()));
    }
}
