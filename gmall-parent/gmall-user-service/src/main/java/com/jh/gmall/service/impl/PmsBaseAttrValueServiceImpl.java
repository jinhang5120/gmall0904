package com.jh.gmall.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jh.gmall.entity.PmsBaseAttrInfo;
import com.jh.gmall.entity.PmsBaseAttrValue;
import com.jh.gmall.mapper.PmsBaseAttrValueMapper;
import com.jh.gmall.service.PmsBaseAttrValueService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 属性值表 服务实现类
 * </p>
 *
 * @author jin
 * @since 2020-09-21
 */
@Service
public class PmsBaseAttrValueServiceImpl extends ServiceImpl<PmsBaseAttrValueMapper, PmsBaseAttrValue> implements PmsBaseAttrValueService {
    @Autowired(required = false)
    PmsBaseAttrValueMapper pmsBaseAttrValueMapper;

    @Override
    public int createAttrValue(List<PmsBaseAttrValue> attrValueList, Long id) {
        int delete = pmsBaseAttrValueMapper.delete(new QueryWrapper<PmsBaseAttrValue>().eq("attr_id", id));
        System.out.println("delete = " + delete);
        for (PmsBaseAttrValue pmsBaseAttrValue : attrValueList) {
//            Integer count = pmsBaseAttrValueMapper.selectCount(new QueryWrapper<PmsBaseAttrValue>().eq("value_name", pmsBaseAttrValue.getValueName()));
//            if(count==0){
                pmsBaseAttrValue.setAttrId(id);
                pmsBaseAttrValueMapper.insert(pmsBaseAttrValue);
//            }
        }
        return 0;
    }

    @Override
    public List<PmsBaseAttrValue> getAttrValueList(int attrId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("attr_id",attrId);
        return pmsBaseAttrValueMapper.selectByMap(map);
    }
}
