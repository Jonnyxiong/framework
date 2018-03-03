package com.jsmsframework.user.service;

import java.util.Map;
import java.util.List;

import com.jsmsframework.common.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsmsframework.user.mapper.JsmsDepartmentMapper;
import com.jsmsframework.user.entity.JsmsDepartment;


import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 部门表
 * @author huangwenjie
 * @date 2017-08-09
 */
@Service
public class JsmsDepartmentServiceImpl implements JsmsDepartmentService{

    private static Logger logger = LoggerFactory.getLogger(JsmsDepartmentServiceImpl.class);


    @Autowired
    private JsmsDepartmentMapper departmentMapper;
    
    @Override
	@Transactional
    public int insert(JsmsDepartment model) {
        return this.departmentMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsDepartment> modelList) {
        return this.departmentMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsDepartment model) {
		JsmsDepartment old = this.departmentMapper.getByDepartmentId(model.getDepartmentId());
		if(old == null){
			return 0;
		}
		return this.departmentMapper.update(model); 
    }

    @Override
	@Transactional
    public int updateSelective(JsmsDepartment model) {
		JsmsDepartment old = this.departmentMapper.getByDepartmentId(model.getDepartmentId());
		if(old != null)
        	return this.departmentMapper.updateSelective(model);
		return 0;
    }

    @Override
	@Transactional
    public JsmsDepartment getByDepartmentId(Integer departmentId) {
        JsmsDepartment model = this.departmentMapper.getByDepartmentId(departmentId);
		return model;
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsDepartment> list = this.departmentMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.departmentMapper.count(params);
    }

    @Override
    public JsmsDepartment getFistLevelDeparment(Integer subDepartmentId) {
        if(subDepartmentId == null)
            return  null;
        JsmsDepartment jsmsDepartment = this.departmentMapper.getByDepartmentId(subDepartmentId);
        if(jsmsDepartment==null||jsmsDepartment.getLevel().equals(1)){
            return jsmsDepartment;
        }
        logger.debug("{}不是一级部门", JsonUtil.toJson(jsmsDepartment));

        int count = jsmsDepartment.getLevel();
        Integer parentId = jsmsDepartment.getParentId();
        while(count>1){
            count--;
            JsmsDepartment upDepartment = this.departmentMapper.getByDepartmentId(parentId);
            if(upDepartment==null||upDepartment.getLevel().equals(1)){
                logger.debug("根据departmentId={}找到一级部门是{}", subDepartmentId,JsonUtil.toJson(upDepartment));
                return upDepartment;
            }
            parentId = upDepartment.getParentId();
            logger.debug("{}不是一级部门", JsonUtil.toJson(upDepartment));
        }

        logger.debug("根据departmentId={}找不到一级部门", subDepartmentId);
        return null;
    }
}
