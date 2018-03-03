package com.jsmsframework.audit.service;

import java.io.File;
import java.util.Map;
import java.util.List;

import com.jsmsframework.audit.entity.JsmsOverrateKeyword;

import com.jsmsframework.common.dto.R;
import com.jsmsframework.common.dto.Result;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 超频关键字表
 * @author huangwenjie
 * @date 2017-10-31
 */
public interface JsmsOverrateKeywordService {

    int insert(JsmsOverrateKeyword model);
    
    int insertBatch(List<JsmsOverrateKeyword> modelList);

    int update(JsmsOverrateKeyword model);
    
    int updateSelective(JsmsOverrateKeyword model);

    JsmsOverrateKeyword getById(Integer id);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String,Object> params);

    Map<String, Object> checkExist(Map<String,Object> param);

    R delOverrate(Integer id);

    R addOverrateKeywordBatch(File uploadFile, String uploadContentType, Long adminId, String tempFileSavePath);
}
