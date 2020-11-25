package com.jh.gmall.service;

import com.jh.gmall.entity.PmsBaseAttrInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashSet;
import java.util.List;

/**
 * <p>
 * 属性表 服务类
 * </p>
 *
 * @author jin
 * @since 2020-09-21
 */
public interface PmsBaseAttrInfoService extends IService<PmsBaseAttrInfo> {

    List<PmsBaseAttrInfo> attrInfoList(int catalog3Id);

    PmsBaseAttrInfo createAttrInfo(PmsBaseAttrInfo pmsBaseAttrInfo);

    List<PmsBaseAttrInfo> selectListBySet(HashSet<Long> attrIdSet);
}
