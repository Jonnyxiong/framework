package com.jsmsframework.sysKeyword.service;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.jsmsframework.sysKeyword.dto.JsmsSysClientGroupDTO;
import com.jsmsframework.sysKeyword.entity.JsmsSysCgroupRefClient;
import com.jsmsframework.sysKeyword.entity.JsmsSysKeywordCategory;
import com.jsmsframework.sysKeyword.entity.JsmsSysKgroupRefCategory;
import com.jsmsframework.sysKeyword.mapper.JsmsSysCgroupRefClientMapper;
import com.jsmsframework.sysKeyword.mapper.JsmsSysKeywordCategoryMapper;
import com.jsmsframework.sysKeyword.mapper.JsmsSysKgroupRefCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsmsframework.sysKeyword.mapper.JsmsSysClientGroupMapper;
import com.jsmsframework.sysKeyword.entity.JsmsSysClientGroup;
import com.jsmsframework.sysKeyword.service.JsmsSysClientGroupService;


import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 系统用户组
 * @author huangwenjie
 * @date 2018-01-15
 */
@Service
public class JsmsSysClientGroupServiceImpl implements JsmsSysClientGroupService{

    @Autowired
    private JsmsSysClientGroupMapper sysClientGroupMapper;
    
    @Override
    public int insert(JsmsSysClientGroup model) {
        return this.sysClientGroupMapper.insert(model);
    }

    @Override
    public int insertBatch(List<JsmsSysClientGroup> modelList) {
        return this.sysClientGroupMapper.insertBatch(modelList);
    }

	@Override
    public int update(JsmsSysClientGroup model) {
		JsmsSysClientGroup old = this.sysClientGroupMapper.getByCgroupId(model.getCgroupId());
		if(old == null){
			return 0;
		}
		return this.sysClientGroupMapper.update(model); 
    }

    @Override
    public int updateSelective(JsmsSysClientGroup model) {
		JsmsSysClientGroup old = this.sysClientGroupMapper.getByCgroupId(model.getCgroupId());
		if(old != null)
        	return this.sysClientGroupMapper.updateSelective(model);
		return 0;
    }

    @Override
    public JsmsSysClientGroup getByCgroupId(Integer cgroupId) {
        JsmsSysClientGroup model = this.sysClientGroupMapper.getByCgroupId(cgroupId);
		return model;
    }

    @Override
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsSysClientGroup> list = this.sysClientGroupMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
    public List<JsmsSysClientGroup> findList(Map params) {
        List<JsmsSysClientGroup> list = this.sysClientGroupMapper.findList(params);
        return list;
    }

    @Override
    public int count(Map<String,Object> params) {
		return this.sysClientGroupMapper.count(params);
    }

    @Override
    public JsmsPage queryListByOtherTable(JsmsPage jsmsPage) {
        List<JsmsSysClientGroupDTO> list = this.sysClientGroupMapper.queryListForDTO(jsmsPage);
        jsmsPage.setData(list);
        return jsmsPage;
    }

    @Override
    public int checkByCGroupName(String cGruopName, Integer cGroupId) {
        return this.sysClientGroupMapper.checkByCGroupName(cGruopName,cGroupId);
    }

    @Override
    public int deleteByCgroupId(Integer cgroupId) {
        return this.sysClientGroupMapper.deleteByCgroupId(cgroupId);
    }

    @Override
    public JsmsSysClientGroup getDefault() {
        return this.sysClientGroupMapper.getDefault();
    }
}
