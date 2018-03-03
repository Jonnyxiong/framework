package com.jsmsframework.sysKeyword.service;

import java.util.*;

import com.jsmsframework.sysKeyword.dto.JsmsSysKeywordGroupDTO;
import com.jsmsframework.sysKeyword.mapper.JsmsSysKeywordCategoryMapper;
import com.jsmsframework.sysKeyword.mapper.JsmsSysKgroupRefCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsmsframework.sysKeyword.mapper.JsmsSysKeywordGroupMapper;
import com.jsmsframework.sysKeyword.entity.JsmsSysKeywordGroup;


import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 系统关键字分组表
 * @author huangwenjie
 * @date 2018-01-15
 */
@Service
public class JsmsSysKeywordGroupServiceImpl implements JsmsSysKeywordGroupService{

    @Autowired
    private JsmsSysKeywordGroupMapper sysKeywordGroupMapper;



    @Autowired
    private JsmsSysKgroupRefCategoryMapper jsmsSysKgroupRefCategoryMapper;
    
    @Autowired
    private JsmsSysKeywordCategoryMapper jsmsSysKeywordCategoryMapper;
    
    @Override
    public int insert(JsmsSysKeywordGroup model) {
        return this.sysKeywordGroupMapper.insert(model);
    }

    @Override
    public int insertBatch(List<JsmsSysKeywordGroup> modelList) {
        return this.sysKeywordGroupMapper.insertBatch(modelList);
    }

	@Override
    public int update(JsmsSysKeywordGroup model) {
		JsmsSysKeywordGroup old = this.sysKeywordGroupMapper.getByKgroupId(model.getKgroupId());
		if(old == null){
			return 0;
		}
		return this.sysKeywordGroupMapper.update(model); 
    }

    @Override
    public int updateSelective(JsmsSysKeywordGroup model) {
		JsmsSysKeywordGroup old = this.sysKeywordGroupMapper.getByKgroupId(model.getKgroupId());
		if(old != null)
        	return this.sysKeywordGroupMapper.updateSelective(model);
		return 0;
    }

    @Override
    public JsmsSysKeywordGroup getByKgroupId(Integer kgroupId) {
        JsmsSysKeywordGroup model = this.sysKeywordGroupMapper.getByKgroupId(kgroupId);
		return model;
    }

    @Override
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsSysKeywordGroup> list = this.sysKeywordGroupMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
    public List<JsmsSysKeywordGroup> findList(Map params) {
        List<JsmsSysKeywordGroup> list = this.sysKeywordGroupMapper.findList(params);
        return list;
    }

    @Override
    public int count(Map<String,Object> params) {
		return this.sysKeywordGroupMapper.count(params);
    }




    @Override
    public int checkKgroupName(String kgroupName, Integer kgroupId) {
        return this.sysKeywordGroupMapper.checkKgroupName(kgroupName,kgroupId);
    }

    @Override
    public int deteleByKgroupId(Integer kgroupId) {
        return this.sysKeywordGroupMapper.deteleByKgroupId(kgroupId);
    }

    @Override
    public List<JsmsSysKeywordGroup> getByGroupIds(Set<Integer> groupIds) {
        return this.sysKeywordGroupMapper.getByGroupIds(groupIds);
    }

    @Override
    public JsmsSysKeywordGroup getDefault() {
        return this.sysKeywordGroupMapper.getDefault();
    }
}
