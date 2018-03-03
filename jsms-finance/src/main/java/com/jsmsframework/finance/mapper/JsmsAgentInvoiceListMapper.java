package com.jsmsframework.finance.mapper;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.interceptor.SimpleCountSQL;
import com.jsmsframework.finance.dto.JsmsAgentInvoiceListDTO;
import com.jsmsframework.finance.entity.JsmsAgentInvoiceList;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
* @author huangwenjie
* @since 2018-01-23
*/
@Repository
public interface JsmsAgentInvoiceListMapper{

	int insert(JsmsAgentInvoiceList model);
	
	int insertBatch(List<JsmsAgentInvoiceList> modelList);

	
	int update(JsmsAgentInvoiceList model);
	
	int updateSelective(JsmsAgentInvoiceList model);

	JsmsAgentInvoiceList getById(Integer id);

	//分页使用的list
	List<JsmsAgentInvoiceList> queryList(JsmsPage page);

	int count(Map<String, Object> params);

	//不分页使用的list(注意：存风险查全库)
	List<JsmsAgentInvoiceList> findList(Map<String, Object> params);

	/**
	 * 获取待开票金额
	 * @param params
	 *    statusList List<Integer> 状态列表
	 *    source Integer 发票来源
	 * @return
	 */
	BigDecimal getWaitInvoiceListAmount(Map<String, Object> params);

	@SimpleCountSQL
	List<JsmsAgentInvoiceList> queryPageList(JsmsPage<JsmsAgentInvoiceList> page);

	JsmsAgentInvoiceList getByIdAndInvoiceType(@Param("id") Integer id, @Param("invoiceType") Integer invoiceType);

	int cancelApply(JsmsAgentInvoiceList model);

	List<JsmsAgentInvoiceList> getAgentInvoiceLists(Map<String, Object> params);

	/**
	  * @Description: 根据申请id获取数据
	  * @Author: tanjiangqiang
	  * @Date: 2018/1/24 - 14:24
	  * @param invoiceId
	  *
	  */
	JsmsAgentInvoiceList getByInvoiceId(@Param("invoiceId") String invoiceId);

	/**
	 * @Description: 更新状态
	 */
	int updateStatus(@Param("newModel")JsmsAgentInvoiceList newModel, @Param("oldModel")JsmsAgentInvoiceList oldModel);
	/**
	 * @Description: 发票信息
	 */
	List<JsmsAgentInvoiceListDTO> queryListForInvoice(JsmsPage page);

}