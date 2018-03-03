package com.jsmsframework.middleware.service;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.jsmsframework.middleware.dto.ComponentRefMqDTO;
import com.jsmsframework.middleware.exception.JsmsComponentRefMqException;
import com.jsmsframework.middleware.exception.JsmsMiddlewareConfigureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsmsframework.middleware.mapper.JsmsComponentRefMqMapper;
import com.jsmsframework.middleware.entity.JsmsComponentRefMq;
import com.jsmsframework.middleware.service.JsmsComponentRefMqService;


import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 组件与MQ关联表
 * @author huangwenjie
 * @date 2017-11-04
 */
@Service
public class JsmsComponentRefMqServiceImpl implements JsmsComponentRefMqService{

    @Autowired
    private JsmsComponentRefMqMapper componentRefMqMapper;
    
    @Override
    public int insert(JsmsComponentRefMq model) {
        return this.componentRefMqMapper.insert(model);
    }

    @Override
    public int insertBatch(List<JsmsComponentRefMq> modelList) {
        return this.componentRefMqMapper.insertBatch(modelList);
    }

	@Override
    public int update(JsmsComponentRefMq model) {
		JsmsComponentRefMq old = this.componentRefMqMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.componentRefMqMapper.update(model); 
    }

    @Override
    public int updateSelective(JsmsComponentRefMq model) {
		JsmsComponentRefMq old = this.componentRefMqMapper.getById(model.getId());
		if(old != null)
        	return this.componentRefMqMapper.updateSelective(model);
		return 0;
    }

    @Override
    public JsmsComponentRefMq getById(Integer id) {
        JsmsComponentRefMq model = this.componentRefMqMapper.getById(id);
		return model;
    }

    @Override
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsComponentRefMq> list = this.componentRefMqMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
    public int count(Map<String,Object> params) {
		return this.componentRefMqMapper.count(params);
    }

    @Override
    public JsmsComponentRefMq getByComponentId(Integer componentId) {
        Map params = new HashMap();
        params.put("componentId",componentId);
        JsmsPage page = new JsmsPage();

        page.setParams(params);
        List<JsmsComponentRefMq> list = this.componentRefMqMapper.queryList(page);

        if(list==null||list.size()==0){
            return null;
        }

        if(list.size()!=1){
            throw new JsmsComponentRefMqException("数据异常");
        }

        return list.get(0);
    }

    /**
     * 组合【组件+消息类型+模式】列表输出
     *
     * @param page
     * @return
     */
    @Override
    public JsmsPage queryList1(JsmsPage page) {
        List<JsmsComponentRefMq> list = this.componentRefMqMapper.queryList1(page);
        page.setData(list);
        return page;
    }

    /**
     * 组合【组件+消息类型+模式】总数
     *
     * @param params
     * @return
     */
    @Override
    public int count1(Map<String, Object> params) {
        return this.componentRefMqMapper.count1(params);
    }

    @Override
    public List<ComponentRefMqDTO> queryByParam(Map<String, Object> params) {
        return null;
    }

    /**
     * 编辑回显SMSP组件及MQ配置相同【组件+消息类型+模式】视图
     *
     * @param componentId
     * @param messageType
     * @param mode
     * @return
     */
    @Override
    public ComponentRefMqDTO queryByCMM(Integer componentId, String messageType, Integer mode) {
        return this.componentRefMqMapper.queryByCMM(componentId,messageType,mode);
    }

}
