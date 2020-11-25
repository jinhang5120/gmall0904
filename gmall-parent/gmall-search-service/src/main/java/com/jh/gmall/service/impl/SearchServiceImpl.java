package com.jh.gmall.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.jh.gmall.entity.PmsSearchParam;
import com.jh.gmall.entity.PmsSearchSkuInfo;
import com.jh.gmall.entity.PmsSkuAttrValue;
import com.jh.gmall.entity.PmsSkuInfo;
import com.jh.gmall.service.SearchService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import io.searchbox.core.search.aggregation.MetricAggregation;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    JestClient jestClient;
    @Override
    public List<PmsSearchSkuInfo> selectList(PmsSearchParam pmsSearchParam) {

        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        if(pmsSearchParam.getKeyword()!=null){
            boolQueryBuilder.must(new MatchQueryBuilder("skuName",pmsSearchParam.getKeyword()));
            HighlightBuilder highlightBuilder=new HighlightBuilder();
            highlightBuilder.field("skuName");//设置高亮的字段
            highlightBuilder.preTags("<span style='color:red'>");
            highlightBuilder.postTags("</span>");
            searchSourceBuilder.highlighter(highlightBuilder);


/*            TermsAggregationBuilder groupby_attr = AggregationBuilders.terms("groupby_attr").field("skuAttrValueList.valueId");
            searchSourceBuilder.aggregation(groupby_attr);*/
        }
        if(pmsSearchParam.getCatalog3Id()!=null){
            boolQueryBuilder.filter(new TermQueryBuilder("catalog3Id",pmsSearchParam.getCatalog3Id()));
        }
        if(pmsSearchParam.getPmsSkuAttrValueList()!=null){
            for (PmsSkuAttrValue pmsSkuAttrValue : pmsSearchParam.getPmsSkuAttrValueList()) {
                boolQueryBuilder.filter(new TermQueryBuilder("skuAttrValueList.valueId",pmsSkuAttrValue.getValueId()));
            }
        }
        if(pmsSearchParam.getValueId()!=null){
            for (Long valueId : pmsSearchParam.getValueId()) {
                boolQueryBuilder.filter(new TermQueryBuilder("skuAttrValueList.valueId",valueId));
            }
        }
        searchSourceBuilder.query(boolQueryBuilder);

//        int from =(skuLsParams.getPageNo()-1)*skuLsParams.getPageSize();
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(20);

        searchSourceBuilder.sort("id", SortOrder.ASC);//搜索排序，按照es中index库type表的字段的顺序

        String query = searchSourceBuilder.toString();

        System.err.println("query = " + query);
        SearchResult searchResult = null;
        try {
            searchResult = jestClient.execute
                    (new Search.Builder(query).addIndex("gmall_index1").addType("PmsSkuInfoType").build());
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<SearchResult.Hit<PmsSearchSkuInfo, Void>> searchResultHits = searchResult.getHits(PmsSearchSkuInfo.class);

        ArrayList<PmsSearchSkuInfo> pmsSearchSkuInfos = new ArrayList<>();
        for (SearchResult.Hit<PmsSearchSkuInfo, Void> searchResultHit : searchResultHits) {
            PmsSearchSkuInfo source = searchResultHit.source;
            Map<String, List<String>> highlight = searchResultHit.highlight;
            if (highlight!=null) {
                String skuName = highlight.get("skuName").get(0);
                source.setSkuName(skuName);
            }
            pmsSearchSkuInfos.add(searchResultHit.source);
        }
        return pmsSearchSkuInfos;
    }
}
