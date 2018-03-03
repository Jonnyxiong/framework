package com.jsmsframework.user.service;

import java.util.Map;
import java.util.List;

import com.jsmsframework.user.entity.JsmsOauthPic;

import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 代理商和客户证件表
 * @author huangwenjie
 * @date 2017-08-16
 */
public interface JsmsOauthPicService {

    int insert(JsmsOauthPic model);
    
    int insertBatch(List<JsmsOauthPic> modelList);

    int update(JsmsOauthPic model);
    
    int updateSelective(JsmsOauthPic model);

    JsmsOauthPic getById(Integer id);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String,Object> params);
    
}
