package com.jh.gmall.service;

import com.jh.gmall.entity.PmsSkuAttrValue;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * sku平台属性值关联表 服务类
 * </p>
 *
 * @author jin
 * @since 2020-09-21
 */
public interface PmsSkuAttrValueService extends IService<PmsSkuAttrValue> {

    void insertPmsSkuAttrValue(PmsSkuAttrValue pmsSkuAttrValue);

    List<PmsSkuAttrValue> selectBySkuId(Long id);
}
