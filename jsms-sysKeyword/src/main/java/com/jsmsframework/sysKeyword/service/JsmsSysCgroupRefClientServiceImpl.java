package com.jsmsframework.sysKeyword.service;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsmsframework.sysKeyword.mapper.JsmsSysCgroupRefClientMapper;
import com.jsmsframework.sysKeyword.entity.JsmsSysCgroupRefClient;
import com.jsmsframework.sysKeyword.service.JsmsSysCgroupRefClientService;


import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 系统用户组内用户表
 * @author huangwenjie
 * @date 2018-01-15
 */
@Service
public class JsmsSysCgroupRefClientServiceImpl implements JsmsSysCgroupRefClientService{

    @Autowired
    private JsmsSysCgroupRefClientMapper sysCgroupRefClientMapper;
    
    @Override
    public int insert(JsmsSysCgroupRefClient model) {
        return this.sysCgroupRefClientMapper.insert(model);
    }

    @Override
    public int insertBatch(List<JsmsSysCgroupRefClient> modelList) {
        return this.sysCgroupRefClientMapper.insertBatch(modelList);
    }

	@Override
    public int update(JsmsSysCgroupRefClient model) {
		JsmsSysCgroupRefClient old = this.sysCgroupRefClientMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.sysCgroupRefClientMapper.update(model); 
    }

    @Override
    public int updateSelective(JsmsSysCgroupRefClient model) {
		JsmsSysCgroupRefClient old = this.sysCgroupRefClientMapper.getById(model.getId());
		if(old != null)
        	return this.sysCgroupRefClientMapper.updateSelective(model);
		return 0;
    }

    @Override
    public JsmsSysCgroupRefClient getById(Integer id) {
        JsmsSysCgroupRefClient model = this.sysCgroupRefClientMapper.getById(id);
		return model;
    }

    @Override
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsSysCgroupRefClient> list = this.sysCgroupRefClientMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
    public List<JsmsSysCgroupRefClient> findList(Map params) {
        List<JsmsSysCgroupRefClient> list = this.sysCgroupRefClientMapper.findList(params);
        return list;
    }

    @Override
    public int count(Map<String,Object> params) {
		return this.sysCgroupRefClientMapper.count(params);
    }

    @Override
    public int deleteByCgroupId(Integer cgroupId) {
        return this.sysCgroupRefClientMapper.deleteByCgroupId(cgroupId);
    }

    @Override
    public List<JsmsSysCgroupRefClient> selectByCgroupId(Integer cgroupId) {
        return this.sysCgroupRefClientMapper.selectByCgroupId(cgroupId);
    }

    @Override
    public JsmsSysCgroupRefClient getByClientId(String clientId) {
        return this.sysCgroupRefClientMapper.getByClientId(clientId);
    }
}
