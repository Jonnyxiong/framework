package com.jsmsframework.schUserConf.service;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.jsmsframework.common.dto.R;
import com.jsmsframework.common.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsmsframework.schUserConf.mapper.JsmsOverrateMapper;
import com.jsmsframework.schUserConf.entity.JsmsOverrate;
import com.jsmsframework.schUserConf.service.JsmsOverrateService;


import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 模板超频表
 * @author huangwenjie
 * @date 2017-09-27
 */
@Service
public class JsmsOverrateServiceImpl implements JsmsOverrateService{
    private  static final Logger LOGGER= LoggerFactory.getLogger(JsmsOverrateServiceImpl.class);
    @Autowired
    private JsmsOverrateMapper overrateMapper;
    
    @Override
	@Transactional
    public int insert(JsmsOverrate model) {
        return this.overrateMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsOverrate> modelList) {
        return this.overrateMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsOverrate model) {
		JsmsOverrate old = this.overrateMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.overrateMapper.update(model); 
    }

    @Override
	@Transactional
    public int updateSelective(JsmsOverrate model) {
		JsmsOverrate old = this.overrateMapper.getById(model.getId());
		if(old != null)
        	return this.overrateMapper.updateSelective(model);
		return 0;
    }

    @Override
	@Transactional
    public JsmsOverrate getById(Integer id) {
        JsmsOverrate model = this.overrateMapper.getById(id);
		return model;
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsOverrate> list = this.overrateMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.overrateMapper.count(params);
    }

    @Override
    public R delOverrate(Integer id) {
        if(id==null){
            return  R.error("关键字超频限制设置ID不存在");
        }
        JsmsOverrate over=this.overrateMapper.getById(id);
        if(over.getId()==null){
            LOGGER.debug("删除关键字超频限制设置 ID={},不存在", id);
            return R.error("关键字超频限制设置不存在");
        }

        int del=this.overrateMapper.delete(id);
        LOGGER.debug( "删除白关键字强制路由通道 ID={}结束,{}", JsonUtil.toJson(id),del > 0 ? "成功删除": "失败删除");
        return del > 0 ? R.ok("删除关键字超频限制设置成功") : R.error("删除关键字超频限制设置失败");
    }

    @Override
    public Map<String, Object> checkExist(Map<String, Object> param) {
        return this.overrateMapper.checkExist(param);
    }

}
