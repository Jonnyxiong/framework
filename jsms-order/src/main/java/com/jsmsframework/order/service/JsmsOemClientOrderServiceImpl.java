package com.jsmsframework.order.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.enums.ProductType;
import com.jsmsframework.common.util.BeanUtil;
import com.jsmsframework.common.util.StringUtils;
import com.jsmsframework.order.dto.OemClientOrderDTO;
import com.jsmsframework.order.dto.OemClientOrderListTotal;
import com.jsmsframework.order.entity.JsmsOemClientOrder;
import com.jsmsframework.order.enums.OrderType;
import com.jsmsframework.order.mapper.JsmsOemClientOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author huangwenjie
 * @description OEM代理商客户订单
 * @date 2017-08-09
 */
@Service
public class JsmsOemClientOrderServiceImpl implements JsmsOemClientOrderService {

    @Autowired
    private JsmsOemClientOrderMapper oemClientOrderMapper;

    @Override
    @Transactional
    public int insert(JsmsOemClientOrder model) {
        return this.oemClientOrderMapper.insert(model);
    }

    @Override
    @Transactional
    public int insertBatch(List<JsmsOemClientOrder> modelList) {
        return this.oemClientOrderMapper.insertBatch(modelList);
    }

    @Override
    @Transactional
    public int update(JsmsOemClientOrder model) {
        JsmsOemClientOrder old = this.oemClientOrderMapper.getByOrderId(model.getOrderId());
        if (old == null) {
            return 0;
        }
        return this.oemClientOrderMapper.update(model);
    }

    @Override
    @Transactional
    public int updateSelective(JsmsOemClientOrder model) {
        JsmsOemClientOrder old = this.oemClientOrderMapper.getByOrderId(model.getOrderId());
        if (old != null)
            return this.oemClientOrderMapper.updateSelective(model);
        return 0;
    }

    @Override
    public JsmsOemClientOrder getByOrderId(Long orderId) {
        JsmsOemClientOrder model = this.oemClientOrderMapper.getByOrderId(orderId);
        return model;
    }

    @Override
    public JsmsPage queryList(JsmsPage page) {
        if (StringUtils.isBlank(page.getOrderByClause())){
            page.setOrderByClause(" create_time desc,order_id DESC ");
        }
        List<JsmsOemClientOrder> list = this.oemClientOrderMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
    public int count(Map<String, Object> params) {
        return this.oemClientOrderMapper.count(params);
    }


    @Override
    public OemClientOrderListTotal queryOemClientOrderListTotal(Map params) {
        OemClientOrderListTotal oemClientOrderListTotal = this.oemClientOrderMapper.queryOemClientOrderListTotal(params);
        if (oemClientOrderListTotal == null) {
            oemClientOrderListTotal = new OemClientOrderListTotal();
            oemClientOrderListTotal.setOrderNumberTotal(0);
            oemClientOrderListTotal.setOrderPriceTotal(BigDecimal.ZERO);
        }
        return oemClientOrderListTotal;
    }


    @Override
    public JsmsPage queryOemClientOrderDTO(JsmsPage page) {
        List<OemClientOrderDTO> result = new ArrayList<>();
        if (StringUtils.isBlank(page.getOrderByClause())){
            page.setOrderByClause(" create_time desc,order_id DESC ");
        }
        List<JsmsOemClientOrder> jsmsOemClientOrders = this.oemClientOrderMapper.queryList(page);
        int i = 0;
        for (JsmsOemClientOrder order : jsmsOemClientOrders) {
            OemClientOrderDTO dto = new OemClientOrderDTO();
            BeanUtil.copyProperties(order, dto);
            if (order.getOrderType().equals(OrderType.充值.getValue())) {
                dto.setOrderTypeName(OrderType.充值.getDesc());
            }

            if (order.getOrderType().equals(OrderType.回退.getValue())) {
                dto.setOrderTypeName(OrderType.回退.getDesc());
            }

            if (order.getOrderType().equals(OrderType.条数返还.getValue())) {
                dto.setOrderTypeName(OrderType.条数返还.getDesc());
            }

            if (order.getProductType().equals(ProductType.行业.getValue())) {
                dto.setProductTypeName(ProductType.行业.getDesc());
                if (order.getOrderType().equals(OrderType.充值.getValue())) {
                    dto.setNumber("+" + dto.getOrderNumber() + "条");
                }

                if (order.getOrderType().equals(OrderType.回退.getValue())) {
                    dto.setNumber("-" + dto.getOrderNumber() + "条");
                }
                if (order.getOrderType().equals(OrderType.条数返还.getValue())) {
                    dto.setNumber("+" + dto.getOrderNumber() + "条");
                }
            }
            if (order.getProductType().equals(ProductType.营销.getValue())) {
                dto.setProductTypeName(ProductType.营销.getDesc());
                if (order.getOrderType().equals(OrderType.充值.getValue())) {
                    dto.setNumber("+" + dto.getOrderNumber() + "条");
                }

                if (order.getOrderType().equals(OrderType.回退.getValue())) {
                    dto.setNumber("-" + dto.getOrderNumber() + "条");
                }
                if (order.getOrderType().equals(OrderType.条数返还.getValue())) {
                    dto.setNumber("+" + dto.getOrderNumber() + "条");
                }
            }
            if (order.getProductType().equals(ProductType.国际.getValue())) {
                dto.setProductTypeName(ProductType.国际.getDesc());
                if (order.getOrderType().equals(OrderType.充值.getValue())) {
                    dto.setNumber("+" + dto.getOrderPrice() + "元");
                }

                if (order.getOrderType().equals(OrderType.回退.getValue())) {
                    dto.setNumber("-" + dto.getOrderPrice() + "元");
                }
                if (order.getOrderType().equals(OrderType.条数返还.getValue())) {
                    dto.setNumber("+" + dto.getOrderPrice() + "元");
                }
            }


            if (order.getProductType().equals(ProductType.通知.getValue())) {
                dto.setProductTypeName(ProductType.通知.getDesc());
                if (order.getOrderType().equals(OrderType.充值.getValue())) {
                    dto.setNumber("+" + dto.getOrderNumber() + "条");
                }

                if (order.getOrderType().equals(OrderType.回退.getValue())) {
                    dto.setNumber("-" + dto.getOrderNumber() + "条");
                }
                if (order.getOrderType().equals(OrderType.条数返还.getValue())) {
                    dto.setNumber("+" + dto.getOrderNumber() + "条");
                }
            }

            if (order.getProductType().equals(ProductType.验证码.getValue())) {
                dto.setProductTypeName(ProductType.验证码.getDesc());
                if (order.getOrderType().equals(OrderType.充值.getValue())) {
                    dto.setNumber("+" + dto.getOrderNumber() + "条");
                }

                if (order.getOrderType().equals(OrderType.回退.getValue())) {
                    dto.setNumber("-" + dto.getOrderNumber() + "条");
                }
                if (order.getOrderType().equals(OrderType.条数返还.getValue())) {
                    dto.setNumber("+" + dto.getOrderNumber() + "条");
                }
            }
            dto.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(dto.getCreateTime()));
            dto.setRemark(dto.getRemark());

            int rownum = (page.getPage() - 1) * page.getRows() + 1 + i;
            i++;
            dto.setRownum(rownum);

            result.add(dto);
        }
        page.setData(result);
        return page;
    }

    @Override
    public List<String> getNewBuyer(Date checkTime, Set<String> clientIds) {
        return this.oemClientOrderMapper.getNewBuyer(checkTime, clientIds);
    }

    @Override
    public List<String> getNewBuyerNew(Date checkTime, List<String> productTypes, String alarmType, Set<String> clientIds) {
        return this.oemClientOrderMapper.getNewBuyerNew(checkTime, productTypes, alarmType, clientIds);
    }

    @Override
    public Integer queryOEMConsumeTotal(Map params) {
        return this.oemClientOrderMapper.queryOEMConsumeTotal(params);
    }
}
