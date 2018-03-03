package com.jsmsframework.user.service;

import java.util.Map;
import java.util.List;

import com.jsmsframework.user.entity.JsmsAgentApply;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.user.entity.JsmsUserFinance;

/**
 * @description 代理商申请表
 * @author huangwenjie
 * @date 2017-08-16
 */
public interface JsmsAgentApplyService {

    int insert(JsmsAgentApply model);
    
    int insertBatch(List<JsmsAgentApply> modelList);

    int update(JsmsAgentApply model);
    
    int updateSelective(JsmsAgentApply model);

    JsmsAgentApply getById(Integer id);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String,Object> params);

    JsmsAgentApply checkEmailAndMobileInApply(String email,String mobile);

    Integer getIdByEmailAndMobile(String email,String mobile);

}
