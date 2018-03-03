package com.jsmsframework.middleware.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.middleware.entity.JsmsMonitorDetailOption;

import java.util.List;
import java.util.Map;

/**
 * @author liangchengan
 * @description 监控明细关联表
 * @date 2017-12-29
 */
public interface JsmsMonitorDetailOptionService {

    int insert(JsmsMonitorDetailOption model);

    int insertBatch(List<JsmsMonitorDetailOption> modelList);

    int update(JsmsMonitorDetailOption model);

    int updateSelective(JsmsMonitorDetailOption model);

    JsmsMonitorDetailOption getById(Integer id);

    JsmsPage queryList(JsmsPage page);

    int count(Map<String, Object> params);

    int delete(Map<String, Object> params);

    /**
     * 更新监控明细关联配置
     *
     * @param model
     * @return
     */
    int updateOption(JsmsMonitorDetailOption model);

    /**
     * 新增监控明细关联配置
     *
     * @param model
     * @return
     */
    int add(JsmsMonitorDetailOption model);

}
