package com.jh.gmall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.beans.Transient;
import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 属性表
 * </p>
 *
 * @author jin
 * @since 2020-09-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PmsBaseAttrInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 属性名称
     */
    private String attrName;

    private Long catalog3Id;

    /**
     * 启用：1 停用：0
     */
    private String isEnabled;

    //不参与远程调用rpc的对象序列化，只在缓存区使用,也不用和数据库进行同步
    private  transient List<PmsBaseAttrValue> attrValueList;
}
