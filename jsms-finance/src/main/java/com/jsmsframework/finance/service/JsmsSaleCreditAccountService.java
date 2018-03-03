package com.jsmsframework.finance.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.finance.entity.JsmsSaleCreditAccount;

import java.util.List;
import java.util.Map;

/**
 * @description 销售授信帐户表
 * @author huangwenjie
 * @date 2017-10-25
 */
public interface JsmsSaleCreditAccountService {

    int insert(JsmsSaleCreditAccount model);
    
    int insertBatch(List<JsmsSaleCreditAccount> modelList);

    int update(JsmsSaleCreditAccount model);
    
    int updateSelective(JsmsSaleCreditAccount model);

    JsmsSaleCreditAccount getBySaleId(Long saleId);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String, Object> params);

    JsmsPage SaleFinQueryList(JsmsPage page);

    Map<String,Object> querySumBlance(Map<String, Object> params);


    /**
     * 考虑事务更新字段,字段正为加，负为减
     * @param model
     * @return
     */
    int updateAccountForRealTime(JsmsSaleCreditAccount model);
}
