package com.jsmsframework.middleware.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.middleware.entity.JsmsComponentConfigure;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Set;

import com.jsmsframework.middleware.exception.JsmsComponentConfigureException;
import com.jsmsframework.middleware.mapper.JsmsComponentConfigureMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;




/**
 * @description 组件配置信息表
 * @author huangwenjie
 * @date 2017-11-04
 */
@Service
public class JsmsComponentConfigureServiceImpl implements JsmsComponentConfigureService{

    @Autowired
    private JsmsComponentConfigureMapper componentConfigureMapper;
    
    @Override
	@Transactional
    public int insert(JsmsComponentConfigure model) {
        return this.componentConfigureMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsComponentConfigure> modelList) {
        return this.componentConfigureMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsComponentConfigure model) {
		JsmsComponentConfigure old = this.componentConfigureMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.componentConfigureMapper.update(model); 
    }

    @Override
	@Transactional
    public int updateSelective(JsmsComponentConfigure model) {
		JsmsComponentConfigure old = this.componentConfigureMapper.getById(model.getId());
		if(old != null)
        	return this.componentConfigureMapper.updateSelective(model);
		return 0;
    }

    @Override
	@Transactional
    public JsmsComponentConfigure getById(Integer id) {
        JsmsComponentConfigure model = this.componentConfigureMapper.getById(id);
		return model;
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsComponentConfigure> list = this.componentConfigureMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.componentConfigureMapper.count(params);
    }


    @Override
    public JsmsComponentConfigure getByComponentId(Integer componentId) {
        Map params = new HashMap();
        params.put("componentId",componentId);
        JsmsPage page = new JsmsPage();

        page.setParams(params);
        List<JsmsComponentConfigure> list = this.componentConfigureMapper.queryList(page);

        if(list==null||list.size()==0){
            return null;
        }

        if(list.size()!=1){
            throw new JsmsComponentConfigureException("数据异常");
        }

        return list.get(0);
    }

    @Override
    public int updateSwitch(Set<Integer> ids, Integer comswitch) {
        return this.componentConfigureMapper.updateSwitch(ids,comswitch);
    }

    @Override
    public List<JsmsComponentConfigure> loadAllForSelectByType(List<String> list) {
        return componentConfigureMapper.loadAllForSelectByType(list);
    }
}
