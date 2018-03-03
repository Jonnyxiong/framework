package com.jsmsframework.stats.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.stats.entity.JsmsClientSuccessRateRealtime;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @description 用户成功率数据统计表
 * @author huangwenjie
 * @date 2017-11-28
 */
public interface JsmsClientSuccessRateRealtimeService {

    int insert(JsmsClientSuccessRateRealtime model);
    
    int insertBatch(List<JsmsClientSuccessRateRealtime> modelList);

    int update(JsmsClientSuccessRateRealtime model);
    
    int updateSelective(JsmsClientSuccessRateRealtime model);

    JsmsClientSuccessRateRealtime getById(Long id);
    
    JsmsPage queryList(JsmsPage page);

    List<JsmsClientSuccessRateRealtime> queryList(Map params);

    /**
     * 查询date之后每个clientid最新的一条记录
     * @param clientIds
     * @param date
     * @return
     */
    List<JsmsClientSuccessRateRealtime> getLastOneStats(List<String> clientIds,Date date);
    
    int count(Map<String, Object> params);
    
}
