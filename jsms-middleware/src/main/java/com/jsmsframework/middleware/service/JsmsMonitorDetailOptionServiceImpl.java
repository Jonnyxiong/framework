package com.jsmsframework.middleware.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.middleware.entity.JsmsMonitorDetailOption;
import com.jsmsframework.middleware.exception.JsmsMonitorDetailOptionException;
import com.jsmsframework.middleware.mapper.JsmsMonitorDetailOptionMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liangchengan
 * @description 监控明细关联表
 * @date 2017-12-29
 */
@Service
public class JsmsMonitorDetailOptionServiceImpl implements JsmsMonitorDetailOptionService {

    @Autowired
    private JsmsMonitorDetailOptionMapper monitorDetailOptionMapper;

    @Override
    public int insert(JsmsMonitorDetailOption model) {
        return this.monitorDetailOptionMapper.insert(model);
    }

    @Override
    public int insertBatch(List<JsmsMonitorDetailOption> modelList) {
        return this.monitorDetailOptionMapper.insertBatch(modelList);
    }

    @Override
    public int update(JsmsMonitorDetailOption model) {
        JsmsMonitorDetailOption old = this.monitorDetailOptionMapper.getById(model.getId());
        if (old == null) {
            return 0;
        }
        return this.monitorDetailOptionMapper.update(model);
    }

    @Override
    public int updateSelective(JsmsMonitorDetailOption model) {
        JsmsMonitorDetailOption old = this.monitorDetailOptionMapper.getById(model.getId());
        if (old != null)
            return this.monitorDetailOptionMapper.updateSelective(model);
        return 0;
    }

    @Override
    public JsmsMonitorDetailOption getById(Integer id) {
        JsmsMonitorDetailOption model = this.monitorDetailOptionMapper.getById(id);
        return model;
    }

    @Override
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsMonitorDetailOption> list = this.monitorDetailOptionMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
    public int count(Map<String, Object> params) {
        return this.monitorDetailOptionMapper.count(params);
    }

    @Override
    public int delete(Map<String, Object> params) {
        return this.monitorDetailOptionMapper.delete(params);
    }

    @Override
    public int updateOption(JsmsMonitorDetailOption model) {
        checkOption(model);
        return this.update(model);
    }

    @Override
    public int add(JsmsMonitorDetailOption model) {
        checkOption(model);
        return this.insert(model);
    }


    /**
     * 校验合法性
     *
     * @param model
     */
    public void checkOption(JsmsMonitorDetailOption model) {
        //列名
        String fieldName = model.getFieldName();
        if (StringUtils.isBlank(fieldName)) {
            throw new JsmsMonitorDetailOptionException("列名不能为空");
        }
        //类型
        String fieldType = String.valueOf(model.getFieldType());
        if (StringUtils.isBlank(fieldType)) {
            throw new JsmsMonitorDetailOptionException("类型不能为空");
        }
        //所属表名
        String measurement = String.valueOf(model.getMeasurement());
        if (StringUtils.isBlank(measurement)) {
            throw new JsmsMonitorDetailOptionException("所属表名不能为空");
        }
        //状态
        String state = String.valueOf(model.getState());
        if (StringUtils.isBlank(state)) {
            int stateInteger = Integer.parseInt(state);
            throw new JsmsMonitorDetailOptionException("状态不能为空");
        }

        //校验唯一性
        Map<String, Object> param = new HashMap<>();
        param.put("fieldName", fieldName);
        param.put("fieldType", fieldType);
        param.put("measurement", measurement);
        //排除自己的id
        String id = String.valueOf(model.getId());
        if (StringUtils.isNotBlank(state)) {
            param.put("exist", id);
        }
        int count = this.count(param);
        if (count > 0) {
            throw new JsmsMonitorDetailOptionException("已存在相同列名、类型、所属表名的配置，请确认");
        }
    }

}
