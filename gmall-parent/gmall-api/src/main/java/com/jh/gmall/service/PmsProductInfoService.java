package com.jh.gmall.service;

import com.jh.gmall.entity.PmsProductInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jin
 * @since 2020-09-21
 */
public interface PmsProductInfoService extends IService<PmsProductInfo> {

    List<PmsProductInfo> spuList(int catalog3Id);

    PmsProductInfo saveSpuInfo(PmsProductInfo pmsProductInfo);
}
