package com.jh.gmall.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jh.gmall.entity.PmsBaseCatalog2;
import com.jh.gmall.mapper.PmsBaseCatalog2Mapper;
import com.jh.gmall.service.PmsBaseCatalog2Service;
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
public class PmsBaseCatalog2ServiceImpl extends ServiceImpl<PmsBaseCatalog2Mapper, PmsBaseCatalog2> implements PmsBaseCatalog2Service {
    @Autowired
    PmsBaseCatalog2Mapper pmsBaseCatalog2Mapper;
    @Override
    public List<PmsBaseCatalog2> getCatalog2(int catalog1Id) {
        QueryWrapper<PmsBaseCatalog2> wrapper = new QueryWrapper<>();
        wrapper.eq("catalog1_id",catalog1Id);//列名必须与数据库表保持一致
        return pmsBaseCatalog2Mapper.selectList(wrapper);
    }
}
