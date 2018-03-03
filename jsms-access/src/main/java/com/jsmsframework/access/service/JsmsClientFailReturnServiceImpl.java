package com.jsmsframework.access.service;

import com.jsmsframework.access.access.entity.JsmsClientFailReturn;
import com.jsmsframework.access.access.mapper.JsmsClientFailReturnMapper;
import com.jsmsframework.common.dto.JsmsPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description 客户失败返回清单表
 * @author huangwenjie
 * @date 2017-10-16
 */
@Service
public class JsmsClientFailReturnServiceImpl implements JsmsClientFailReturnService{

    @Autowired
    private JsmsClientFailReturnMapper clientFailReturnMapper;
    
    @Override
    public int insert(JsmsClientFailReturn model) {
        return this.clientFailReturnMapper.insert(model);
    }

    @Override
    public int insertBatch(List<JsmsClientFailReturn> modelList) {
        return this.clientFailReturnMapper.insertBatch(modelList);
    }

	@Override
    public int update(JsmsClientFailReturn model) {
		JsmsClientFailReturn old = this.clientFailReturnMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.clientFailReturnMapper.update(model); 
    }

    @Override
    public int updateSelective(JsmsClientFailReturn model) {
		JsmsClientFailReturn old = this.clientFailReturnMapper.getById(model.getId());
		if(old != null)
        	return this.clientFailReturnMapper.updateSelective(model);
		return 0;
    }

    @Override
    public int updateStatus(Integer refundState, List<Integer> idList) {
        return this.clientFailReturnMapper.updateStatus(refundState, idList);
    }

    @Override
    public JsmsClientFailReturn getById(Integer id) {
        JsmsClientFailReturn model = this.clientFailReturnMapper.getById(id);
		return model;
    }

    @Override
    public List<JsmsClientFailReturn> getBySubIds(Set<String> subIds) {
        List<JsmsClientFailReturn> list = this.clientFailReturnMapper.getBySubIds(subIds);
        return list;
    }

    @Override
    public List<JsmsClientFailReturn> getByIds(Set<Integer> ids) {
        List<JsmsClientFailReturn> list = this.clientFailReturnMapper.getByIds(ids);
        return list;
    }

    @Override
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsClientFailReturn> list = this.clientFailReturnMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
    public int count(Map<String,Object> params) {
		return this.clientFailReturnMapper.count(params);
    }


    @Override
    public List<JsmsClientFailReturn> queryAll(Map<String, Object> params) {
        return this.clientFailReturnMapper.queryAll(params);
    }
}
