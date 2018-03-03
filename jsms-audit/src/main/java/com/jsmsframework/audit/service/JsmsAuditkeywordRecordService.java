package com.jsmsframework.audit.service;

import java.util.Date;
import java.util.Map;
import java.util.List;

import com.jsmsframework.audit.entity.JsmsAuditkeywordRecord;

import com.jsmsframework.common.dto.Result;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 短信审核与关键字记录表
 * @author huangwenjie
 * @date 2017-09-12
 */
public interface JsmsAuditkeywordRecordService {

    int insert(JsmsAuditkeywordRecord model);
    
    int insertBatch(List<JsmsAuditkeywordRecord> modelList);

    int update(JsmsAuditkeywordRecord model);
    
    int updateSelective(JsmsAuditkeywordRecord model);

    JsmsAuditkeywordRecord getById(Integer id);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String,Object> params);

    List<JsmsAuditkeywordRecord> queryAll(Map<String,Object> params);

    List<JsmsAuditkeywordRecord> queryAllRemoveRecordAndCreatetime(Date dateDate);

    JsmsAuditkeywordRecord queryByIdAndCreateTime(Long id, Date createtime);

    int delByIdAndCreateTime(Long id, Date createtime);

    int insertWithTableName(JsmsAuditkeywordRecord jsmsAuditkeywordRecord, String tableName);
    
}
