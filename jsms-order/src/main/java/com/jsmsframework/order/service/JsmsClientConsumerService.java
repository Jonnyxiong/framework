package com.jsmsframework.order.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.order.dto.ClientConsumerListTotal;

import java.util.Map;

/**
 * Created by Administrator on 2017/10/18.
 */
public interface JsmsClientConsumerService {
    ClientConsumerListTotal queryClientOrderListTotal(Map params);

    JsmsPage queryClientOrderDTO(JsmsPage page);
}
