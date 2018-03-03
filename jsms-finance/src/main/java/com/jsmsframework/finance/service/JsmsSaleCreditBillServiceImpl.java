package com.jsmsframework.finance.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Objects;

import com.jsmsframework.finance.enums.FinancialType;
import com.jsmsframework.finance.enums.ObjectType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsmsframework.finance.mapper.JsmsSaleCreditBillMapper;
import com.jsmsframework.finance.entity.JsmsSaleCreditBill;
import com.jsmsframework.finance.service.JsmsSaleCreditBillService;


import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 销售授信收支明细
 * @author huangwenjie
 * @date 2017-10-25
 */
@Service
public class JsmsSaleCreditBillServiceImpl implements JsmsSaleCreditBillService{

    @Autowired
    private JsmsSaleCreditBillMapper saleCreditBillMapper;
    
    @Override
	@Transactional
    public int insert(JsmsSaleCreditBill model) {
        return this.saleCreditBillMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsSaleCreditBill> modelList) {
        return this.saleCreditBillMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsSaleCreditBill model) {
		JsmsSaleCreditBill old = this.saleCreditBillMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.saleCreditBillMapper.update(model); 
    }

    @Override
	@Transactional
    public int updateSelective(JsmsSaleCreditBill model) {
		JsmsSaleCreditBill old = this.saleCreditBillMapper.getById(model.getId());
		if(old != null)
        	return this.saleCreditBillMapper.updateSelective(model);
		return 0;
    }

    @Override
	@Transactional
    public JsmsSaleCreditBill getById(Integer id) {
        JsmsSaleCreditBill model = this.saleCreditBillMapper.getById(id);
		return model;
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsSaleCreditBill> list = this.saleCreditBillMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.saleCreditBillMapper.count(params);
    }

    @Override
    public String getSumAmount(Map<String, Object> params) {
        List<JsmsSaleCreditBill> bills=this.queryAll(params);
        BigDecimal sum=BigDecimal.ZERO;
        for (JsmsSaleCreditBill bill : bills) {
            if(Objects.equals(bill.getFinancialType(), FinancialType.入账.getValue())){
                sum=sum.add(bill.getAmount());
            }else if(Objects.equals(bill.getFinancialType(),FinancialType.出账.getValue())){
                sum=sum.subtract(bill.getAmount());
            }
        }
        return sum.toString();
    }

    @Override
    public List<JsmsSaleCreditBill> queryAll(Map<String, Object> params) {
        return this.saleCreditBillMapper.queryAll(params);
    }

    @Override
    public JsmsSaleCreditBill total(Map<String, String> params) {
        return saleCreditBillMapper.total(params);
    }

    @Override
    public JsmsPage queryHisCreditList(JsmsPage page) {
        List<JsmsSaleCreditBill> list = this.saleCreditBillMapper.queryHisCreditList(page);
        page.setData(list);
        return page;
    }

    @Override
    public JsmsPage queryCreditBillList(JsmsPage page) {
        List<JsmsSaleCreditBill> list = this.saleCreditBillMapper.queryCreditBillList(page);
        page.setData(list);
        return page;
    }

    @Override
    public JsmsSaleCreditBill totalCreditBill(Map<String, String> params) {
        return saleCreditBillMapper.totalCreditBill(params);
    }

    @Override
    public JsmsPage queryCreditBillListOfCustomer(JsmsPage page) {
        List<JsmsSaleCreditBill> list = this.saleCreditBillMapper.queryCreditBillListOfCustomer(page);
        page.setData(list);
        return page;
    }

    @Override
    public JsmsSaleCreditBill totalCreditBillOfCustomer(Map<String, String> params) {
        return saleCreditBillMapper.totalCreditBillOfCustomer(params);
    }

}
