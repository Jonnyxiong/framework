package com.jsmsframework.user.service;

import java.util.Map;
import java.util.List;

import com.jsmsframework.user.entity.JsmsAccountLoginStatus;

import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 用户登陆状态表
 * @author huangwenjie
 * @date 2017-08-16
 */
public interface JsmsAccountLoginStatusService {

    int insert(JsmsAccountLoginStatus model);
    
    int insertBatch(List<JsmsAccountLoginStatus> modelList);

    int update(JsmsAccountLoginStatus model);
    
    int updateSelective(JsmsAccountLoginStatus model);

    JsmsAccountLoginStatus getById(Integer id);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String,Object> params);
    
}
