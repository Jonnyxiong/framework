package com.jsmsframework.common.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.entity.JsmsParam;
import com.jsmsframework.common.mapper.JsmsParamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @description 参数表
 * @author huangwenjie
 * @date 2017-08-16
 */
@Service
public class JsmsParamServiceImpl implements JsmsParamService{

    @Autowired
    private JsmsParamMapper paramMapper;
    
    @Override
	@Transactional
    public int insert(JsmsParam model) {
        return this.paramMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsParam> modelList) {
        return this.paramMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsParam model) {
		JsmsParam old = this.paramMapper.getByParamId(model.getParamId());
		if(old == null){
			return 0;
		}
		return this.paramMapper.update(model); 
    }

    @Override
	@Transactional
    public int updateSelective(JsmsParam model) {
		JsmsParam old = this.paramMapper.getByParamId(model.getParamId());
		if(old != null)
        	return this.paramMapper.updateSelective(model);
		return 0;
    }

    @Override
	@Transactional
    public JsmsParam getByParamId(Long paramId) {
        JsmsParam model = this.paramMapper.getByParamId(paramId);
		return model;
    }

    @Override
    public List<JsmsParam> getByParamKey(String paramKey) {
        return this.paramMapper.getByParamKey(paramKey);
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsParam> list = this.paramMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.paramMapper.count(params);
    }

   /* @Override
    public JsmsParam getByParamKey(String paramKey) {
        return this.paramMapper.getByParamKey(paramKey);
    }*/

}
