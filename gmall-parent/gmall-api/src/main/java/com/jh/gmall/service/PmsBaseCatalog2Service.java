package com.jh.gmall.service;

import com.jh.gmall.entity.PmsBaseCatalog2;
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
public interface PmsBaseCatalog2Service extends IService<PmsBaseCatalog2> {

    List<PmsBaseCatalog2> getCatalog2(int catalog1Id);
}
