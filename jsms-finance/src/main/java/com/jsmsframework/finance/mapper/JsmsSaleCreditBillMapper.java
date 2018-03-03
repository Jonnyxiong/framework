package com.jsmsframework.finance.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import com.jsmsframework.finance.entity.JsmsSaleCreditBill;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 销售授信收支明细
 * @author huangwenjie
 * @date 2017-10-25
 */
@Repository
public interface JsmsSaleCreditBillMapper{

	int insert(JsmsSaleCreditBill model);
	
	int insertBatch(List<JsmsSaleCreditBill> modelList);

	
	int update(JsmsSaleCreditBill model);
	
	int updateSelective(JsmsSaleCreditBill model);

    JsmsSaleCreditBill getById(Integer id);

	List<JsmsSaleCreditBill> queryList(JsmsPage<JsmsSaleCreditBill> page);

	int count(Map<String,Object> params);

	List<JsmsSaleCreditBill> queryAll(Map<String,Object> params);

	/*
    * 销售授信合计
    * */
	JsmsSaleCreditBill total(@Param(value="params") Map<String, String> params);

	/*
* 获取销售历史授信数据
* */
	List<JsmsSaleCreditBill> queryHisCreditList(JsmsPage page);
	/*
   * 获取销售授信账单数据
   * */
	List<JsmsSaleCreditBill> queryCreditBillList(JsmsPage page);
	/*
    * 销售授信账单合计
    * */
	JsmsSaleCreditBill totalCreditBill(@Param(value="params") Map<String, String> params);

	/*
      * 获取客户授信账单数据
      * */
	List<JsmsSaleCreditBill> queryCreditBillListOfCustomer(JsmsPage page);
	/*
    * 客户授信账单合计
    * */
	JsmsSaleCreditBill totalCreditBillOfCustomer(@Param(value="params")Map<String, String> params);

}