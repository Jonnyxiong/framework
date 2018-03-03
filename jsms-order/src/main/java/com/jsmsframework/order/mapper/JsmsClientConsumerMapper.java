package com.jsmsframework.order.mapper;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.order.dto.ClientConsumerListTotal;
import com.jsmsframework.order.entity.JsmsClientConsumer;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by xiongfenglin on 2017/10/18.
 */
public interface JsmsClientConsumerMapper {
    ClientConsumerListTotal queryClientOrderListTotal(@Param("params") Map params);

    List<JsmsClientConsumer> queryList(JsmsPage<JsmsClientConsumer> page);

}