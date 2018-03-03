package com.jsmsframework.common.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.entity.JsmsSendEmailList;

import java.util.List;
import java.util.Map;

/**
 * @description 发送邮箱管理表
 * @author tanjiangqiang
 * @date 2017-11-30
 */
public interface JsmsSendEmailListService {

    int insert(JsmsSendEmailList model);
    
    int insertBatch(List<JsmsSendEmailList> modelList);

    int update(JsmsSendEmailList model);
    
    int updateSelective(JsmsSendEmailList model);

    JsmsSendEmailList getById(Integer id);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String, Object> params);

    int countForEdit(Map<String, Object> params);
}
