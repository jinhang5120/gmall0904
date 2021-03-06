package com.jh.gmall.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jh.gmall.entity.PmsBaseAttrInfo;
import com.jh.gmall.entity.PmsBaseAttrValue;
import com.jh.gmall.mapper.PmsBaseAttrInfoMapper;
import com.jh.gmall.mapper.PmsBaseAttrValueMapper;
import com.jh.gmall.service.PmsBaseAttrInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * <p>
 * 属性表 服务实现类
 * </p>
 *
 * @author jin
 * @since 2020-09-21
 */
@Service
public class PmsBaseAttrInfoServiceImpl extends ServiceImpl<PmsBaseAttrInfoMapper, PmsBaseAttrInfo> implements PmsBaseAttrInfoService {
    @Autowired
    PmsBaseAttrInfoMapper pmsBaseAttrInfoMapper;
    @Override
    public List<PmsBaseAttrInfo> attrInfoList(int catalog3Id) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("catalog3_id",catalog3Id);
        return pmsBaseAttrInfoMapper.selectByMap(map);
    }

    @Override
    public PmsBaseAttrInfo createAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo) {
        System.out.println("pmsBaseAttrInfoMapper.insert(pmsBaseAttrInfo) = " + pmsBaseAttrInfoMapper.insert(pmsBaseAttrInfo));
        QueryWrapper<PmsBaseAttrInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("attr_name",pmsBaseAttrInfo.getAttrName());
        return pmsBaseAttrInfoMapper.selectOne(wrapper);
    }

    @Override
    public List<PmsBaseAttrInfo> selectListBySet(HashSet<Long> attrIdSet) {
        return pmsBaseAttrInfoMapper.selectBatchIds(attrIdSet);
    }
}
