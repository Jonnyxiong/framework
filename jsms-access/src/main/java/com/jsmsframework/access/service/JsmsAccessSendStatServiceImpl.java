package com.jsmsframework.access.service;

import com.jsmsframework.access.access.entity.JsmsAccessSendStat;
import com.jsmsframework.access.access.mapper.JsmsAccessSendStatMapper;
import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @description 客户发送量表
 * @author huangwenjie
 * @date 2017-10-16
 */
@Service
public class JsmsAccessSendStatServiceImpl implements JsmsAccessSendStatService{

    @Autowired
    private JsmsAccessSendStatMapper accessSendStatMapper;
    
    @Override
	@Transactional
    public int insert(JsmsAccessSendStat model) {
        return this.accessSendStatMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsAccessSendStat> modelList) {
        return this.accessSendStatMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsAccessSendStat model) {
		JsmsAccessSendStat old = this.accessSendStatMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.accessSendStatMapper.update(model); 
    }

    @Override
	@Transactional
    public int updateSelective(JsmsAccessSendStat model) {
		JsmsAccessSendStat old = this.accessSendStatMapper.getById(model.getId());
		if(old != null)
        	return this.accessSendStatMapper.updateSelective(model);
		return 0;
    }

    @Override
    public JsmsAccessSendStat getById(Integer id) {
        JsmsAccessSendStat model = this.accessSendStatMapper.getById(id);
		return model;
    }

    @Override
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsAccessSendStat> list = this.accessSendStatMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
    public int count(Map<String,Object> params) {
		return this.accessSendStatMapper.count(params);
    }


    @Override
    public JsmsPage querySumList(JsmsPage page,String groupByClause) throws IllegalAccessException {
        if(StringUtils.isBlank(groupByClause)){
            throw new IllegalAccessException("请指定分组条件groupByClause");
        }
        page.setGroupByClause(groupByClause);
        List<JsmsAccessSendStat> list = this.accessSendStatMapper.querySumList(page);
        page.setData(list);
        return page;
    }
}
