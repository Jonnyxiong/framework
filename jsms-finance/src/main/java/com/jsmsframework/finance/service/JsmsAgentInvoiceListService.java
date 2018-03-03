package com.jsmsframework.finance.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.finance.dto.JsmsAgentInvoiceListDTO;
import com.jsmsframework.finance.entity.JsmsAgentInvoiceList;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
* @author huangwenjie
* @since 2018-01-23
*/
public interface JsmsAgentInvoiceListService {

    int insert(JsmsAgentInvoiceList model);
    
    int insertBatch(List<JsmsAgentInvoiceList> modelList);

    int update(JsmsAgentInvoiceList model);
    
    int updateSelective(JsmsAgentInvoiceList model);

    JsmsAgentInvoiceList getById(Integer id);

    JsmsPage queryList(JsmsPage page);
    
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

    /**
      * @Description: 根据申请id获取数据
      * @Author: tanjiangqiang
      * @Date: 2018/1/24 - 12:07
      * @param invoiceId 申请id
      *
      */
    JsmsAgentInvoiceList getByInvoiceId(String invoiceId);

    /**
     * 更新状态
     * @param newModel
     * @param oldModel
     * @return
     */
    int updateStatus(JsmsAgentInvoiceList newModel,JsmsAgentInvoiceList oldModel);


    List<JsmsAgentInvoiceListDTO> queryListForInvoice(JsmsPage page);

    /**
     * 取消申请
     * @param model
     * @return
     */
    int cancelApply(JsmsAgentInvoiceList model);
}
