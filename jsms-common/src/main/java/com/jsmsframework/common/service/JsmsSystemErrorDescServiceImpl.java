package com.jsmsframework.common.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.entity.JsmsSystemErrorDesc;
import com.jsmsframework.common.mapper.JsmsSystemErrorDescMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @description 平台错误码对应表
 * @author huangwenjie
 * @date 2017-11-25
 */
@Service
public class JsmsSystemErrorDescServiceImpl implements JsmsSystemErrorDescService{

    @Autowired
    private JsmsSystemErrorDescMapper systemErrorDescMapper;
    
    @Override
    public int insert(JsmsSystemErrorDesc model) {
        return this.systemErrorDescMapper.insert(model);
    }

    @Override
    public int insertBatch(List<JsmsSystemErrorDesc> modelList) {
        return this.systemErrorDescMapper.insertBatch(modelList);
    }

	@Override
    public int update(JsmsSystemErrorDesc model) {
		JsmsSystemErrorDesc old = this.systemErrorDescMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.systemErrorDescMapper.update(model); 
    }

    @Override
    public int updateSelective(JsmsSystemErrorDesc model) {
		JsmsSystemErrorDesc old = this.systemErrorDescMapper.getById(model.getId());
		if(old != null)
        	return this.systemErrorDescMapper.updateSelective(model);
		return 0;
    }

    @Override
    public JsmsSystemErrorDesc getById(Integer id) {
        JsmsSystemErrorDesc model = this.systemErrorDescMapper.getById(id);
		return model;
    }

    @Override
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsSystemErrorDesc> list = this.systemErrorDescMapper.queryList(page);
        page.setData(list);
        return page;
    }
    @Override
    public List<JsmsSystemErrorDesc> queryAllList(Map<String,Object> params) {
        List<JsmsSystemErrorDesc> list = this.systemErrorDescMapper.queryAllList(params);
        return list;
    }

    @Override
    public int count(Map<String,Object> params) {
		return this.systemErrorDescMapper.count(params);
    }

    @Override
    public JsmsSystemErrorDesc getBySyscode(String syscode) {
        return systemErrorDescMapper.getBySyscode(syscode);
    }

}
