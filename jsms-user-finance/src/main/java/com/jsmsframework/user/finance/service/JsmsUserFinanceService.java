package com.jsmsframework.user.finance.service;

import com.jsmsframework.common.dto.R;
import com.jsmsframework.common.enums.WebId;
import com.jsmsframework.finance.dto.JsmsAgentInvoiceListDTO;
import com.jsmsframework.finance.entity.JsmsAgentInvoiceConfig;
import com.jsmsframework.finance.entity.JsmsAgentInvoiceList;
import com.jsmsframework.user.entity.JsmsUserFinance;
import com.jsmsframework.common.dto.JsmsPage;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by xiongfenglin on 2017/11/23.
 *
 * @author: xiongfenglin
 */
public interface JsmsUserFinanceService {
    /**
   * 用户注册
   *
   * */
    //isCheckInviteCode为true时要校验邀请码   为false时不校验（页面点击继续提交后不需要校验）
    R oemAgentRegister(JsmsUserFinance jsmsUserFinance,boolean isCheckInviteCode);
    /**
     * 获取销售列表
     *
     * */
    /**
     * 获取销售id
     * @return
     */
    List<String> getSaleList();

    /**
     * 获取可开票金额
     */
    BigDecimal getCanInvoiceAmount(Integer agentId);

    /**
     * 获取可退款金额
     *
     * @param agentId
     * @return
     */
    BigDecimal getCanBackAmount(Integer agentId);

    /**
     * 查询发票申请记录
     * @param page
     * @param webId
     * @return
     */
    JsmsPage queryPageList(JsmsPage page,WebId webId);

    /**
     * 查看信息
     * @param id
     * @param webId
     * @param invoiceType
     * @return
     */
    JsmsAgentInvoiceListDTO checkDetailedInformation(Integer id, Integer invoiceType, WebId webId);

    /**
     * 获取满足条件的所有数据
     * @param params
     * @return
     */
    List<JsmsAgentInvoiceListDTO> getAgentInvoiceLists(Map<String, Object> params);

    R applicationInvoice(JsmsAgentInvoiceList invoice);

    JsmsAgentInvoiceConfig findListAdd(Integer agingId);

    JsmsAgentInvoiceConfig findListNomal(Integer agingId);
}
