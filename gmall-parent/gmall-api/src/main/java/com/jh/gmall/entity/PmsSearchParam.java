package com.jh.gmall.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class PmsSearchParam implements Serializable {
    String keyword;
    Long catalog3Id;
    List<PmsSkuAttrValue> pmsSkuAttrValueList;
    Long[] valueId;
}
