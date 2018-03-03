package com.jsmsframework.finance.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.finance.entity.JsmsUserPriceLog;

import java.util.List;
import java.util.Map;

/**
 * @description 后付费用户价格变更记录
 * @author huangwenjie
 * @date 2017-08-08
 */
public interface JsmsUserPriceLogService {

    public int insert(JsmsUserPriceLog model);
    
    public int insertBatch(List<JsmsUserPriceLog> modelList);

    public int update(JsmsUserPriceLog model);
    
    public int updateSelective(JsmsUserPriceLog model);
    
    public JsmsUserPriceLog getById(Integer id);
    
    public JsmsPage queryList(JsmsPage page);
    
    public int count(Map<String, Object> params);

    public List<JsmsUserPriceLog> getuserPrice(String clientid,Integer smstype);
    
}
