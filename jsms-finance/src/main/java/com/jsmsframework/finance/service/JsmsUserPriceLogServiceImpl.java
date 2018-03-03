package com.jsmsframework.finance.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.finance.entity.JsmsUserPriceLog;
import com.jsmsframework.finance.mapper.JsmsUserPriceLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @description 后付费用户价格变更记录
 * @author huangwenjie
 * @date 2017-08-08
 */
@Service
public class JsmsUserPriceLogServiceImpl implements JsmsUserPriceLogService {

    @Autowired
    private JsmsUserPriceLogMapper jsmsUserPriceLogMapper;
    
    @Override
	@Transactional
    public int insert(JsmsUserPriceLog model) {
        return this.jsmsUserPriceLogMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsUserPriceLog> modelList) {
        return this.jsmsUserPriceLogMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsUserPriceLog model) {
		JsmsUserPriceLog old = this.jsmsUserPriceLogMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.jsmsUserPriceLogMapper.update(model);
    }

    @Override
	@Transactional
    public int updateSelective(JsmsUserPriceLog model) {
		JsmsUserPriceLog old = this.jsmsUserPriceLogMapper.getById(model.getId());
		if(old != null)
        	return this.jsmsUserPriceLogMapper.updateSelective(model);
		return 0;
    }

    @Override
	@Transactional
    public JsmsUserPriceLog getById(Integer id) {
        JsmsUserPriceLog model = this.jsmsUserPriceLogMapper.getById(id);
		return model;
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsUserPriceLog> list = this.jsmsUserPriceLogMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.jsmsUserPriceLogMapper.count(params);
    }

    @Override
    public List<JsmsUserPriceLog> getuserPrice(String clientid,Integer smstype) {
        return jsmsUserPriceLogMapper.getuserPrice(clientid,smstype);
    }

}
