package com.jsmsframework.common.service;

import java.util.Map;
import java.util.List;

import com.jsmsframework.common.entity.JsmsNoticeList;

import com.jsmsframework.common.dto.Result;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 公告管理表
 * @author huangwenjie
 * @date 2017-12-06
 */
public interface JsmsNoticeListService {

    int insert(JsmsNoticeList model);
    
    int insertBatch(List<JsmsNoticeList> modelList);

    int update(JsmsNoticeList model);
    
    int updateSelective(JsmsNoticeList model);

    JsmsNoticeList getById(Integer id);
    
    JsmsPage queryList(JsmsPage page);

    JsmsPage queryPageList(JsmsPage page);
    
    int count(Map<String, Object> params);

    JsmsNoticeList getContentById(Integer id);

    List<JsmsNoticeList> queryListAll(Map<String, Object> params);
    
}
