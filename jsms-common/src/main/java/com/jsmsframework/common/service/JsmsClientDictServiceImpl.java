package com.jsmsframework.common.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.entity.JsmsClientDict;
import com.jsmsframework.common.mapper.JsmsClientDictMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author huangwenjie
 * @description 用户数据字典表
 * @date 2017-11-07
 */
@Service
public class JsmsClientDictServiceImpl implements JsmsClientDictService {

    @Autowired
    private JsmsClientDictMapper clientDictMapper;

    @Override
    public int insert(JsmsClientDict model) {
        return this.clientDictMapper.insert(model);
    }

    @Override
    public int insertBatch(List<JsmsClientDict> modelList) {
        return this.clientDictMapper.insertBatch(modelList);
    }

    @Override
    public int update(JsmsClientDict model) {
        JsmsClientDict old = this.clientDictMapper.getByParamId(model.getParamId());
        if (old == null) {
            return 0;
        }
        return this.clientDictMapper.update(model);
    }

    @Override
    public int updateSelective(JsmsClientDict model) {
        JsmsClientDict old = this.clientDictMapper.getByParamId(model.getParamId());
        if (old != null)
            return this.clientDictMapper.updateSelective(model);
        return 0;
    }

    @Override
    public JsmsClientDict getByParamId(Integer paramId) {
        JsmsClientDict model = this.clientDictMapper.getByParamId(paramId);
        return model;
    }

    @Override
    public List<JsmsClientDict> getByParamType(String paramType) {
        List<JsmsClientDict> list = this.clientDictMapper.getByParamType(paramType);
        return list;
    }

    @Override

    public JsmsPage queryList(JsmsPage page) {
        List<JsmsClientDict> list = this.clientDictMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
    public int count(Map<String, Object> params) {
        return this.clientDictMapper.count(params);
    }

}
