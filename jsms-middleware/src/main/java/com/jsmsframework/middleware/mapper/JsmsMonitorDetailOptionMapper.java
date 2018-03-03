package com.jsmsframework.middleware.mapper;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.middleware.entity.JsmsMonitorDetailOption;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author liangchengan
 * @description 监控明细关联表
 * @date 2017-12-29
 */
@Repository
public interface JsmsMonitorDetailOptionMapper {

    int insert(JsmsMonitorDetailOption model);

    int insertBatch(List<JsmsMonitorDetailOption> modelList);


    int update(JsmsMonitorDetailOption model);

    int updateSelective(JsmsMonitorDetailOption model);

    JsmsMonitorDetailOption getById(Integer id);

    List<JsmsMonitorDetailOption> queryList(JsmsPage<JsmsMonitorDetailOption> page);

    int count(Map<String, Object> params);

    int delete(Map<String, Object> params);

}