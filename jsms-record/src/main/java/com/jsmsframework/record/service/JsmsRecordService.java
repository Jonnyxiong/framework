package com.jsmsframework.record.service;

import java.util.List;
import java.util.Map;


import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.record.record.entity.JsmsRecord;

/**
 * @description 短信记录流水表
 * @author Don
 * @date 2018-01-11
 */
public interface JsmsRecordService {

    int insert(JsmsRecord model);
    
    int insertBatch(List<JsmsRecord> modelList);

   /* int update(JsmsRecord model);*/
    
    /*int updateSelective(JsmsRecord model);*/

    JsmsRecord getBySmsuuid(String smsuuid,String tableName);
    
    JsmsPage queryList(JsmsPage page);

    List<JsmsRecord> findList(Map params);

    int count(Map<String,Object> params);
    /**
     * 查找失败重发记录
     * @return
     */
    JsmsPage queryFailRecord(JsmsPage page);
}
