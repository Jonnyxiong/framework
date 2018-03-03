package com.jsmsframework.finance.service;

import java.util.Map;
import java.util.List;

import com.jsmsframework.finance.entity.JsmsSaleCreditBill;

import com.jsmsframework.common.dto.Result;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 销售授信收支明细
 * @author huangwenjie
 * @date 2017-10-25
 */
public interface JsmsSaleCreditBillService {

    int insert(JsmsSaleCreditBill model);
    
    int insertBatch(List<JsmsSaleCreditBill> modelList);

    int update(JsmsSaleCreditBill model);
    
    int updateSelective(JsmsSaleCreditBill model);

    JsmsSaleCreditBill getById(Integer id);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String,Object> params);

    String getSumAmount(Map<String, Object> params);

    List<JsmsSaleCreditBill> queryAll(Map<String,Object> params);
    /*
    * 销售授信合计
    * */
    JsmsSaleCreditBill total(Map<String, String> params);
    /*
    * 获取销售授信数据
    * */
    JsmsPage queryHisCreditList(JsmsPage page);

    /*
    * 获取销售授信账单数据
    * */
    JsmsPage queryCreditBillList(JsmsPage page);
    /*
    * 销售授信账单合计totalCreditBill
    * */
    JsmsSaleCreditBill totalCreditBill(Map<String, String> params);
    /*
       * 获取客户授信账单数据
       * */
    JsmsPage queryCreditBillListOfCustomer(JsmsPage page);
    /*
    * 客户授信账单合计
    * */
    JsmsSaleCreditBill totalCreditBillOfCustomer(Map<String, String> params);
}
