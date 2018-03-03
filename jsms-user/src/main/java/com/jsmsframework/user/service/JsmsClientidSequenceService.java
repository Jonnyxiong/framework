package com.jsmsframework.user.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.user.entity.JsmsClientidSequence;

import java.util.List;
import java.util.Map;

/**
 * @description 客户账号clientid序列表
 * @author huangwenjie
 * @date 2017-08-16
 */
public interface JsmsClientidSequenceService {

    int insert(JsmsClientidSequence model);
    
    int insertBatch(List<JsmsClientidSequence> modelList);

    int update(JsmsClientidSequence model);
    
    int updateSelective(JsmsClientidSequence model);

    JsmsClientidSequence getById(Long id);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String,Object> params);
    
    /**
     * @Description: 获取公用的clientid序列(如果序列表中有未使用的数据则直接取,否则添加10,000条数据)
     * @author: Niu.T
     * @date: 2016年9月6日 下午4:21:00
     * @return: String
     */
    String getOrAddId();

    /**
     * @Description: 修改clientid状态为1，表示已经使用
     * @param clientId
     */
    boolean updateClientIdStatus(String clientId);
}
