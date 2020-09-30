package com.jh.gmall.service;

import com.jh.gmall.entity.PmsProductImage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品图片表 服务类
 * </p>
 *
 * @author jin
 * @since 2020-09-21
 */
public interface PmsProductImageService extends IService<PmsProductImage> {

    void saveProductImage(PmsProductImage pmsProductImage);

    List<PmsProductImage> spuImageList(int spuId);
}
