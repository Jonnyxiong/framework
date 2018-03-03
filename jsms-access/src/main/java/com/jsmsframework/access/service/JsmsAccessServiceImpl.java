package com.jsmsframework.access.service;

import com.jsmsframework.access.access.entity.JsmsAccess;
import com.jsmsframework.access.access.mapper.JsmsAccessMapper;
import com.jsmsframework.common.dto.JsmsPage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @description Access流水表
 * @author huangwenjie
 * @date 2017-10-20
 */
@Service
public class JsmsAccessServiceImpl implements JsmsAccessService {

    @Autowired
    private JsmsAccessMapper accessMapper;

    @Override
    public int insert(JsmsAccess model) {
        return this.accessMapper.insert(model);
    }

    @Override
    public int insertBatch(List<JsmsAccess> modelList) {
        return this.accessMapper.insertBatch(modelList);
    }

	@Override
    public int update(JsmsAccess model) {
		JsmsAccess old = this.accessMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.accessMapper.update(model);
    }

    @Override
    public int updateSelective(JsmsAccess model) {
		JsmsAccess old = this.accessMapper.getById(model.getId());
		if(old != null)
        	return this.accessMapper.updateSelective(model);
		return 0;
    }

    @Override
    public JsmsAccess getById(String id) {
        JsmsAccess model = this.accessMapper.getById(id);
		return model;
    }

    @Override
    public JsmsPage queryOneDayList(String identify,String suffixDate,JsmsPage jsmsPage){
        if(StringUtils.isBlank(identify) || !identify.matches("^\\d$")){
            throw new IllegalArgumentException("无效的identify --> " + identify);
        }
        if(StringUtils.isBlank(suffixDate) || !suffixDate.matches("^\\d{8}$")){
            throw new IllegalArgumentException("无效的流水表后缀日期 格式(yyyyMMdd)--> " + suffixDate);
        }
        jsmsPage.getParams().put("identify", identify);
        jsmsPage.getParams().put("suffixDate", suffixDate);
        List<JsmsAccess> list = this.accessMapper.queryOneDayList(jsmsPage);
        jsmsPage.setData(list);
        return jsmsPage;
    }

    @Override
    public int count(Map<String,Object> params) {
		return this.accessMapper.count(params);
    }

}
