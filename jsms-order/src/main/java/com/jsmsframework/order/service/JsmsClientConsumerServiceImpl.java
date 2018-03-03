package com.jsmsframework.order.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.enums.ProductType;
import com.jsmsframework.common.util.BeanUtil;
import com.jsmsframework.order.dto.ClientConsumerDTO;
import com.jsmsframework.order.dto.ClientConsumerListTotal;
import com.jsmsframework.order.entity.JsmsClientConsumer;
import com.jsmsframework.order.enums.OrderType;
import com.jsmsframework.order.mapper.JsmsClientConsumerMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by xiongfenglin on 2017/10/18.
 */
@Service
public class JsmsClientConsumerServiceImpl implements JsmsClientConsumerService{
    @Autowired
    private JsmsClientConsumerMapper clientConsumerMapper;
    @Override
    public ClientConsumerListTotal queryClientOrderListTotal(Map params) {
        ClientConsumerListTotal clientOrderListTotal = this.clientConsumerMapper.queryClientOrderListTotal(params);
        if (clientOrderListTotal == null) {
            clientOrderListTotal = new ClientConsumerListTotal();
            clientOrderListTotal.setOrderNumberTotal(0);
            clientOrderListTotal.setOrderPriceTotal(BigDecimal.ZERO);
        }
        return clientOrderListTotal;
    }
    @Override
    public JsmsPage queryClientOrderDTO(JsmsPage page) {
        List<ClientConsumerDTO> result = new ArrayList<>();
        if (StringUtils.isBlank(page.getOrderByClause())){
            page.setOrderByClause(" operate_time desc,id DESC ");
        }
        List<JsmsClientConsumer> jsmsClientOrders = this.clientConsumerMapper.queryList(page);
        int i = 0;
        for (JsmsClientConsumer order : jsmsClientOrders) {
            ClientConsumerDTO dto = new ClientConsumerDTO();
            BeanUtil.copyProperties(order, dto);

            if (order.getOperateType().equals(0)) {

                dto.setOrderTypeName(OrderType.条数返还.getDesc());
            }

            if (order.getProductType().equals(ProductType.行业.getValue())) {
                dto.setProductTypeName(ProductType.行业.getDesc());
                if (order.getOperateType().equals(0)) {
                    dto.setNumber("+" + dto.getSmsNumber() + "条");
                }
            }
            if (order.getProductType().equals(ProductType.营销.getValue())) {
                dto.setProductTypeName(ProductType.营销.getDesc());
                if (order.getOperateType().equals(0)) {
                    dto.setNumber("+" + dto.getSmsNumber() + "条");
                }
            }
            if (order.getProductType().equals(ProductType.国际.getValue())) {
                dto.setProductTypeName(ProductType.国际.getDesc());
                if (order.getOperateType().equals(0)) {
                    dto.setNumber("+" + dto.getSmsNumber() + "条");
                }
            }


            if (order.getProductType().equals(ProductType.通知.getValue())) {
                dto.setProductTypeName(ProductType.通知.getDesc());
                if (order.getOperateType().equals(0)) {
                    dto.setNumber("+" + dto.getSmsNumber() + "条");
                }
            }

            if (order.getProductType().equals(ProductType.验证码.getValue())) {
                dto.setProductTypeName(ProductType.验证码.getDesc());
                if (order.getOperateType().equals(0)) {
                    dto.setNumber("+" + dto.getSmsNumber() + "条");
                }
            }
            dto.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(dto.getOperateTime()));
            dto.setRemark(dto.getRemark());
            int rownum = (page.getPage() - 1) * page.getRows() + 1 + i;
            i++;
            dto.setRownum(rownum);

            result.add(dto);
        }
        page.setData(result);
        return page;
    }
}
