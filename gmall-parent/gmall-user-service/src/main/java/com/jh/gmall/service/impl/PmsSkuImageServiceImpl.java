package com.jh.gmall.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jh.gmall.entity.PmsSkuImage;
import com.jh.gmall.mapper.PmsSkuImageMapper;
import com.jh.gmall.service.PmsSkuImageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>
 * 库存单元图片表 服务实现类
 * </p>
 *
 * @author jin
 * @since 2020-09-21
 */
@Service
public class PmsSkuImageServiceImpl extends ServiceImpl<PmsSkuImageMapper, PmsSkuImage> implements PmsSkuImageService {
    @Autowired
    PmsSkuImageMapper pmsSkuImageMapper;
    @Override
    public void insertSkuImage(PmsSkuImage pmsSkuImage) {
        System.out.println("pmsSkuImageMapper.insert(pmsSkuImage) = " + pmsSkuImageMapper.insert(pmsSkuImage));
    }

    @Override
    public List<PmsSkuImage> selectList(Long skuId) {
        return pmsSkuImageMapper.selectList(new QueryWrapper<PmsSkuImage>().eq("sku_id",skuId));
    }
}
