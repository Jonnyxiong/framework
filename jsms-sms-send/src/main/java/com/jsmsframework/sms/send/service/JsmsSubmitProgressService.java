package com.jsmsframework.sms.send.service;


import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.enums.WebId;
import com.jsmsframework.sms.send.entity.JsmsSubmitProgress;

import java.util.List;
import java.util.Map;

/**
 * @description 短信提交进度表
 * @author Don
 * @date 2018-01-18
 */
public interface JsmsSubmitProgressService {

    int insert(JsmsSubmitProgress model);
    
    int insertBatch(List<JsmsSubmitProgress> modelList);

    /**
     * @param id 提交进度表, 主键id
     * @param sendNum 提交到后台http组件的数量
     */
    int updateProgress(int id,int sendNum);

    /**
     * 更新完成提交状态的记录
     */
    int finishSubmitState();

    int update(JsmsSubmitProgress model);
    
    int updateSelective(JsmsSubmitProgress model);

    JsmsSubmitProgress getById(Integer id);
    
    JsmsPage queryList(JsmsPage page);

    List<JsmsSubmitProgress> findList(Map params);

    int count(Map<String, Object> params);

    /**
     * 用户中心查询定时任务
     * @param page
     * @return
     */
    JsmsPage queryPageList(JsmsPage page, WebId webId, String id);

}
