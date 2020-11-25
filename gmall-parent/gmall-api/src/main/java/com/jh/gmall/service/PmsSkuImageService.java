package com.jh.gmall.service;

import com.jh.gmall.entity.PmsSkuImage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 库存单元图片表 服务类
 * </p>
 *
 * @author jin
 * @since 2020-09-21
 */
public interface PmsSkuImageService extends IService<PmsSkuImage> {

    void insertSkuImage(PmsSkuImage pmsSkuImage);

    List<PmsSkuImage> selectList(Long skuId);
}
