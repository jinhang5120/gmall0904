package com.jh.gmall.service;

import com.jh.gmall.entity.PmsBaseCatalog3;
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
public interface PmsBaseCatalog3Service extends IService<PmsBaseCatalog3> {

    List<PmsBaseCatalog3> getCatalog3(int catalog2Id);
}
