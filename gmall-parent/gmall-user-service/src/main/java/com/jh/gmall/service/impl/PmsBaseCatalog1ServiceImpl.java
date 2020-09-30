package com.jh.gmall.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.jh.gmall.entity.PmsBaseCatalog1;
import com.jh.gmall.mapper.PmsBaseCatalog1Mapper;
import com.jh.gmall.service.PmsBaseCatalog1Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>
 * 一级分类表 服务实现类
 * </p>
 *
 * @author jin
 * @since 2020-09-21
 */
@Service
public class PmsBaseCatalog1ServiceImpl extends ServiceImpl<PmsBaseCatalog1Mapper, PmsBaseCatalog1> implements PmsBaseCatalog1Service {
    @Autowired(required = false)
    PmsBaseCatalog1Mapper pmsBaseCatalog1Mapper;
    @Override
    public List<PmsBaseCatalog1> getCatalog1() {
        return pmsBaseCatalog1Mapper.selectList(null);
    }
}
