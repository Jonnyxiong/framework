package com.jsmsframework.finance.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.finance.entity.JsmsSaleCreditAccount;
import com.jsmsframework.finance.mapper.JsmsSaleCreditAccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @description 销售授信帐户表
 * @author huangwenjie
 * @date 2017-10-25
 */
@Service
public class JsmsSaleCreditAccountServiceImpl implements JsmsSaleCreditAccountService {

    @Autowired
    private JsmsSaleCreditAccountMapper saleCreditAccountMapper;
    
    @Override
	@Transactional
    public int insert(JsmsSaleCreditAccount model) {
        return this.saleCreditAccountMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsSaleCreditAccount> modelList) {
        return this.saleCreditAccountMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsSaleCreditAccount model) {
		JsmsSaleCreditAccount old = this.saleCreditAccountMapper.getBySaleId(model.getSaleId());
		if(old == null){
			return 0;
		}
		return this.saleCreditAccountMapper.update(model); 
    }

    @Override
	@Transactional
    public int updateSelective(JsmsSaleCreditAccount model) {
		JsmsSaleCreditAccount old = this.saleCreditAccountMapper.getBySaleId(model.getSaleId());
		if(old != null)
        	return this.saleCreditAccountMapper.updateSelective(model);
		return 0;
    }

    @Override
	@Transactional
    public JsmsSaleCreditAccount getBySaleId(Long saleId) {
        JsmsSaleCreditAccount model = this.saleCreditAccountMapper.getBySaleId(saleId);
		return model;
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsSaleCreditAccount> list = this.saleCreditAccountMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.saleCreditAccountMapper.count(params);
    }

    @Override
    public JsmsPage SaleFinQueryList(JsmsPage page) {
        List<JsmsSaleCreditAccount> list = this.saleCreditAccountMapper.SaleFinQueryList(page);
        page.setData(list);
        return page;
    }

    @Override
    public Map<String, Object> querySumBlance(Map<String, Object> params) {
        return this.saleCreditAccountMapper.querySumBlance(params);
    }

    /**
     * 考虑事务更新字段,字段正为加，负为减
     *
     * @param model
     * @return
     */
    @Override
    public int updateAccountForRealTime(JsmsSaleCreditAccount model) {
        return this.saleCreditAccountMapper.updateAccountForRealTime(model);
    }

}
