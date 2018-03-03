package com.jsmsframework.finance.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import com.jsmsframework.finance.entity.JsmsSaleCreditAccount;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 销售授信帐户表
 * @author huangwenjie
 * @date 2017-10-25
 */
@Repository
public interface JsmsSaleCreditAccountMapper{

	int insert(JsmsSaleCreditAccount model);
	
	int insertBatch(List<JsmsSaleCreditAccount> modelList);

	
	int update(JsmsSaleCreditAccount model);
	
	int updateSelective(JsmsSaleCreditAccount model);

    JsmsSaleCreditAccount getBySaleId(Long saleId);

	List<JsmsSaleCreditAccount> queryList(JsmsPage<JsmsSaleCreditAccount> page);

	int count(Map<String, Object> params);

	List<JsmsSaleCreditAccount> SaleFinQueryList(JsmsPage<JsmsSaleCreditAccount> page);

	Map<String,Object> querySumBlance(Map<String, Object> params);

	int updateAccountForRealTime(JsmsSaleCreditAccount model);
}