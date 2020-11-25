package com.jh.gmall.service;

import com.jh.gmall.entity.PmsSearchParam;
import com.jh.gmall.entity.PmsSearchSkuInfo;
import com.jh.gmall.entity.PmsSkuAttrValue;

import java.util.List;

public interface SearchService {
    List<PmsSearchSkuInfo> selectList(PmsSearchParam pmsSearchParam);
}
