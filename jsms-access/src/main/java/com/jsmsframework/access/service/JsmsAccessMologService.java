package com.jsmsframework.access.service;

import com.jsmsframework.access.access.entity.JsmsAccessMolog;
import com.jsmsframework.common.dto.JsmsPage;

import java.util.List;
import java.util.Map;

/**
 * @description 客户上行明细表
 * @author huangwenjie
 * @date 2017-11-28
 */
public interface JsmsAccessMologService {

    int insert(JsmsAccessMolog model);
    
    int insertBatch(List<JsmsAccessMolog> modelList);

    int update(JsmsAccessMolog model);
    
    int updateSelective(JsmsAccessMolog model);

    JsmsAccessMolog getByMoid(String moid);

    JsmsAccessMolog getByMoid(String moid,String dateSuffix);

    /**
     * 用户中心5.19.2 , 对应调度系统上行回复记录开始分表
     * @param page
     * @param dateSuffix 日期后缀, 格式为 yyyyMM
     * @return
     */
    JsmsPage queryList(JsmsPage page,String dateSuffix);

    /**
     * 只能查询出当前月份的数据
     * @param page
     * @return
     */
    @Deprecated
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String, Object> params);
    
}
