package com.jsmsframework.middleware.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.middleware.entity.JsmsMiddlewareConfigure;
import com.jsmsframework.middleware.exception.JsmsMiddlewareConfigureException;
import com.jsmsframework.middleware.mapper.JsmsMiddlewareConfigureMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description 中间件配置信息表
 * @author huangwenjie
 * @date 2017-11-04
 */
@Service
public class JsmsMiddlewareConfigureServiceImpl implements JsmsMiddlewareConfigureService{

    @Autowired
    private JsmsMiddlewareConfigureMapper middlewareConfigureMapper;
    
    @Override
	@Transactional
    public int insert(JsmsMiddlewareConfigure model) {
        return this.middlewareConfigureMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsMiddlewareConfigure> modelList) {
        return this.middlewareConfigureMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsMiddlewareConfigure model) {
		JsmsMiddlewareConfigure old = this.middlewareConfigureMapper.getByMiddlewareId(model.getMiddlewareId());
		if(old == null){
			return 0;
		}
		return this.middlewareConfigureMapper.update(model); 
    }

    @Override
	@Transactional
    public int updateSelective(JsmsMiddlewareConfigure model) {
		JsmsMiddlewareConfigure old = this.middlewareConfigureMapper.getByMiddlewareId(model.getMiddlewareId());
		if(old != null)
        	return this.middlewareConfigureMapper.updateSelective(model);
		return 0;
    }

    @Override
	@Transactional
    public JsmsMiddlewareConfigure getByMiddlewareId(Integer middlewareId) {
        JsmsMiddlewareConfigure model = this.middlewareConfigureMapper.getByMiddlewareId(middlewareId);
		return model;
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsMiddlewareConfigure> list = this.middlewareConfigureMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.middlewareConfigureMapper.count(params);
    }


    @Override
    public JsmsMiddlewareConfigure getByMiddlewareType(Integer middlewareType) {
        Map middleParams = new HashMap<>();
        middleParams.put("middlewareType", middlewareType);
        JsmsPage jsmsPage = new JsmsPage();
        jsmsPage.setParams(middleParams);
        List<JsmsMiddlewareConfigure> list = this.middlewareConfigureMapper.queryList(jsmsPage);

        if(list==null||list.size()==0){
            return null;
        }

        if(list.size()!=1){
            throw new JsmsMiddlewareConfigureException("数据异常");
        }

        return list.get(0);
    }
}
