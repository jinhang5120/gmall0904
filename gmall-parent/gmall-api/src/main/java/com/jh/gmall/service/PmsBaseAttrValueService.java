package com.jh.gmall.service;

import com.jh.gmall.entity.PmsBaseAttrValue;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashSet;
import java.util.List;

/**
 * <p>
 * 属性值表 服务类
 * </p>
 *
 * @author jin
 * @since 2020-09-21
 */
public interface PmsBaseAttrValueService extends IService<PmsBaseAttrValue> {

    int createAttrValue(List<PmsBaseAttrValue> attrValueList, Long id);

    List<PmsBaseAttrValue> getAttrValueList(Long attrId);

    List<PmsBaseAttrValue> selectListBySet(HashSet<Long> valueIdSet);

    PmsBaseAttrValue getValueNameById(Long valueId);
}
