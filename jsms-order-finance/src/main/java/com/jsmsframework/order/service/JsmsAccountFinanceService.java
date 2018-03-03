package com.jsmsframework.order.service;

import java.util.List;

public interface JsmsAccountFinanceService {
    //测试短信赠送
    public void giveShortMessage(String clientid,Integer paytype,Integer agentId,String name, List<Long> oemClientOrders,Integer test_product_id,Integer test_sms_number,Integer oemDataId);

}
