package com.jh.gmall.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jh.gmall.entity.PmsProductImage;
import com.jh.gmall.mapper.PmsProductImageMapper;
import com.jh.gmall.service.PmsProductImageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>
 * 商品图片表 服务实现类
 * </p>
 *
 * @author jin
 * @since 2020-09-21
 */
@Service
public class PmsProductImageServiceImpl extends ServiceImpl<PmsProductImageMapper, PmsProductImage> implements PmsProductImageService {
    @Autowired(required = false)
    PmsProductImageMapper pmsProductImageMapper;

    @Override
    public void saveProductImage(PmsProductImage pmsProductImage) {
        System.out.println("pmsProductImageMapper.insert(pmsProductImage) = " + pmsProductImageMapper.insert(pmsProductImage));
    }

    @Override
    public List<PmsProductImage> spuImageList(int spuId) {
        return pmsProductImageMapper.selectList(new QueryWrapper<PmsProductImage>().eq("product_id",spuId));
    }

    @Override
    public PmsProductImage selectOne(PmsProductImage pmsProductImage) {
        return pmsProductImageMapper.selectOne(new QueryWrapper<PmsProductImage>().setEntity(pmsProductImage));
    }
}
