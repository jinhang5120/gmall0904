package com.jh.gmall.service;

import com.jh.gmall.entity.PmsBaseCatalog1;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 一级分类表 服务类
 * </p>
 *
 * @author jin
 * @since 2020-09-21
 */
public interface PmsBaseCatalog1Service extends IService<PmsBaseCatalog1> {

    List<PmsBaseCatalog1> getCatalog1();
}
