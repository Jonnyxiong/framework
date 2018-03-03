package com.jsmsframework.access.service;

import com.jsmsframework.access.access.entity.JsmsClientFailReturn;
import com.jsmsframework.common.dto.JsmsPage;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description 客户失败返回清单表
 * @author huangwenjie
 * @date 2017-10-16
 */
public interface JsmsClientFailReturnService {

    int insert(JsmsClientFailReturn model);
    
    int insertBatch(List<JsmsClientFailReturn> modelList);

    int update(JsmsClientFailReturn model);
    
    int updateSelective(JsmsClientFailReturn model);

    int updateStatus(Integer refundState, List<Integer> idList);

    JsmsClientFailReturn getById(Integer id);

    List<JsmsClientFailReturn> getBySubIds(Set<String> subIds);

    List<JsmsClientFailReturn> getByIds(Set<Integer> ids);

    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String, Object> params);

    List<JsmsClientFailReturn> queryAll(Map<String,Object> params);
    
}
