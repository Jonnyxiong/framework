package com.jsmsframework.channel.service;

import java.util.Map;
import java.util.List;

import com.jsmsframework.channel.entity.JsmsKeywordList;

import com.jsmsframework.common.dto.Result;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 关键字表
 * @author huangwenjie
 * @date 2017-12-06
 */
public interface JsmsKeywordListService {

    int insert(JsmsKeywordList model);
    
    int insertBatch(List<JsmsKeywordList> modelList);

    int update(JsmsKeywordList model);
    
    int updateSelective(JsmsKeywordList model);

    JsmsKeywordList getById(Long id);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String,Object> params);

    String checkKeyword(String content);
}
