package com.jh.gmall.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jh.gmall.entity.PmsBaseCatalog2;
import com.jh.gmall.entity.PmsBaseCatalog3;
import com.jh.gmall.mapper.PmsBaseCatalog2Mapper;
import com.jh.gmall.mapper.PmsBaseCatalog3Mapper;
import com.jh.gmall.service.PmsBaseCatalog3Service;
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
public class PmsBaseCatalog3ServiceImpl extends ServiceImpl<PmsBaseCatalog3Mapper, PmsBaseCatalog3> implements PmsBaseCatalog3Service {

    @Autowired(required = false)
    PmsBaseCatalog3Mapper pmsBaseCatalog3Mapper;
    @Override
    public List<PmsBaseCatalog3> getCatalog3(int catalog2Id) {
        QueryWrapper<PmsBaseCatalog3> wrapper = new QueryWrapper<>();
        wrapper.eq("catalog2_id",catalog2Id);//列名必须与数据库表保持一致
        return pmsBaseCatalog3Mapper.selectList(wrapper);
    }
}
