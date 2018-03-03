package com.jsmsframework.user.service;

import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsmsframework.user.mapper.JsmsAccountLoginStatusMapper;
import com.jsmsframework.user.entity.JsmsAccountLoginStatus;


import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 用户登陆状态表
 * @author huangwenjie
 * @date 2017-08-16
 */
@Service
public class JsmsAccountLoginStatusServiceImpl implements JsmsAccountLoginStatusService{

    @Autowired
    private JsmsAccountLoginStatusMapper accountLoginStatusMapper;
    
    @Override
	@Transactional
    public int insert(JsmsAccountLoginStatus model) {
        return this.accountLoginStatusMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsAccountLoginStatus> modelList) {
        return this.accountLoginStatusMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsAccountLoginStatus model) {
		JsmsAccountLoginStatus old = this.accountLoginStatusMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.accountLoginStatusMapper.update(model); 
    }

    @Override
	@Transactional
    public int updateSelective(JsmsAccountLoginStatus model) {
		JsmsAccountLoginStatus old = this.accountLoginStatusMapper.getById(model.getId());
		if(old != null)
        	return this.accountLoginStatusMapper.updateSelective(model);
		return 0;
    }

    @Override
	@Transactional
    public JsmsAccountLoginStatus getById(Integer id) {
        JsmsAccountLoginStatus model = this.accountLoginStatusMapper.getById(id);
		return model;
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsAccountLoginStatus> list = this.accountLoginStatusMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.accountLoginStatusMapper.count(params);
    }
    
}
