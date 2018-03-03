package com.jsmsframework.common.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.entity.JsmsSendEmailList;
import com.jsmsframework.common.mapper.JsmsSendEmailListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @description 发送邮箱管理表
 * @author tanjiangqiang
 * @date 2017-11-30
 */
@Service
public class JsmsSendEmailListServiceImpl implements JsmsSendEmailListService{

    @Autowired
    private JsmsSendEmailListMapper sendEmailListMapper;
    
    @Override
	@Transactional
    public int insert(JsmsSendEmailList model) {
        return this.sendEmailListMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsSendEmailList> modelList) {
        return this.sendEmailListMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsSendEmailList model) {
		JsmsSendEmailList old = this.sendEmailListMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.sendEmailListMapper.update(model); 
    }

    @Override
	@Transactional
    public int updateSelective(JsmsSendEmailList model) {
		JsmsSendEmailList old = this.sendEmailListMapper.getById(model.getId());
		if(old != null)
        	return this.sendEmailListMapper.updateSelective(model);
		return 0;
    }

    @Override
    public JsmsSendEmailList getById(Integer id) {
        JsmsSendEmailList model = this.sendEmailListMapper.getById(id);
		return model;
    }

    @Override
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsSendEmailList> list = this.sendEmailListMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
    public int count(Map<String,Object> params) {
		return this.sendEmailListMapper.count(params);
    }

    @Override
    public int countForEdit(Map<String,Object> params) {
        return this.sendEmailListMapper.countForEdit(params);
    }

}
