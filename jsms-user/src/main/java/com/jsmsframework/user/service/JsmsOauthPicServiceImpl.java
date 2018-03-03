package com.jsmsframework.user.service;

import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsmsframework.user.mapper.JsmsOauthPicMapper;
import com.jsmsframework.user.entity.JsmsOauthPic;


import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 代理商和客户证件表
 * @author huangwenjie
 * @date 2017-08-16
 */
@Service
public class JsmsOauthPicServiceImpl implements JsmsOauthPicService{

    @Autowired
    private JsmsOauthPicMapper oauthPicMapper;
    
    @Override
	@Transactional
    public int insert(JsmsOauthPic model) {
        return this.oauthPicMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsOauthPic> modelList) {
        return this.oauthPicMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsOauthPic model) {
		JsmsOauthPic old = this.oauthPicMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.oauthPicMapper.update(model); 
    }

    @Override
	@Transactional
    public int updateSelective(JsmsOauthPic model) {
		JsmsOauthPic old = this.oauthPicMapper.getById(model.getId());
		if(old != null)
        	return this.oauthPicMapper.updateSelective(model);
		return 0;
    }

    @Override
	@Transactional
    public JsmsOauthPic getById(Integer id) {
        JsmsOauthPic model = this.oauthPicMapper.getById(id);
		return model;
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsOauthPic> list = this.oauthPicMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.oauthPicMapper.count(params);
    }
    
}
