package com.jh.gmall;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jh.gmall.entity.PmsSearchSkuInfo;
import com.jh.gmall.entity.PmsSkuInfo;
import com.jh.gmall.service.PmsSkuAttrValueService;
import com.jh.gmall.service.PmsSkuInfoService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class Test02 {
    @Autowired
    JestClient jestClient;
    @Reference
    PmsSkuInfoService pmsSkuInfoService;
    @Reference
    PmsSkuAttrValueService pmsSkuAttrValueService;
    @Test
    public void m1() throws IOException {
        System.out.println("jestClient = " + jestClient);
        System.out.println();
        List<PmsSkuInfo> pmsSkuInfoList = pmsSkuInfoService.selectAll();
        List<PmsSearchSkuInfo> pmsSearchSkuInfoList = new ArrayList<>();
        for (PmsSkuInfo pmsSkuInfo : pmsSkuInfoList) {
            pmsSkuInfo.setSkuAttrValueList(pmsSkuAttrValueService.selectBySkuId(pmsSkuInfo.getId()));
            PmsSearchSkuInfo pmsSearchSkuInfo = new PmsSearchSkuInfo();
            BeanUtils.copyProperties(pmsSkuInfo,pmsSearchSkuInfo);
            pmsSearchSkuInfoList.add(pmsSearchSkuInfo);
            jestClient.execute(new Index.Builder(pmsSearchSkuInfo).index("gmall_index1").type("PmsSkuInfoType").id(pmsSearchSkuInfo.getId()+"").build());
        }
        System.out.println("pmsSearchSkuInfoList = " + pmsSearchSkuInfoList);
    }
    @Test
    public void m2() throws IOException {
        SearchResult searchResult = jestClient.execute(new Search.Builder("{\n" +
                "  \"query\": {\n" +
                "    \"bool\": {\n" +
                "      \"filter\": [\n" +
                "        {\"terms\": {\"skuAttrValueList.valueId\": [\"39\",\"40\"]}},\n" +
                "        {\"term\": {\"skuAttrValueList.attrId\": \"25\"}}\n" +
                "      ],\n" +
                "      \"must\": {\"match\": {\"skuName\": \"小米\"}}\n" +
                "    }\n" +
                "  }\n" +
                "}")
                .addIndex("gmall_index1").addType("PmsSkuInfoType").build());
        List<SearchResult.Hit<PmsSearchSkuInfo, Void>> searchResultHits = searchResult.getHits(PmsSearchSkuInfo.class);
        for (SearchResult.Hit<PmsSearchSkuInfo, Void> searchResultHit : searchResultHits) {
            System.out.println("searchResultHit.source = " + searchResultHit.source);
        }
        System.out.println("searchResultHits.size() = " + searchResultHits.size());
    }
}
