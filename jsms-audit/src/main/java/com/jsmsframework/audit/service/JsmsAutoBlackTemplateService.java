package com.jsmsframework.audit.service;

import java.util.Map;
import java.util.List;

import com.jsmsframework.audit.dto.JsmsBalckTemplateDTO;
import com.jsmsframework.audit.entity.JsmsAutoBlackTemplate;

import com.jsmsframework.common.dto.R;
import com.jsmsframework.common.dto.Result;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 智能黑模板表
 * @author huangwenjie
 * @date 2017-11-30
 */
public interface JsmsAutoBlackTemplateService {

    int insert(JsmsAutoBlackTemplate model);
    
    int insertBatch(List<JsmsAutoBlackTemplate> modelList);

    int update(JsmsAutoBlackTemplate model);
    
    int updateSelective(JsmsAutoBlackTemplate model);

    JsmsAutoBlackTemplate getByTemplateId(Integer templateId);
    
    JsmsPage queryList(JsmsPage page);
    
    int count(Map<String,Object> params);

    /**
     * 添加通用、用户黑模板
     * @param balckTemplatedto
     * @return
     */
    R addBalackTemplate(JsmsBalckTemplateDTO balckTemplatedto);

    /**
     * 根据条件判断是否存在黑模板
     * @param balckTemplate
     * @return
     */
    boolean isExistBalckTemplate(JsmsAutoBlackTemplate balckTemplate);

    /**
     * 根据条件查询符合的黑模板
     * @param balckTemplate
     * @return
     */
    List<JsmsAutoBlackTemplate> queryAll(JsmsBalckTemplateDTO balckTemplate);

    /**
     * 删除黑模板
     * @param templateId
     * @return
     */
    R delBalckTemplate(Integer templateId);

    /**
     * 启用、失效黑模板
     * @param templateId
     * @param state
     * @return
     */
    R updateState4BalckTemplate(Integer templateId,Integer state);

    /**
     * 编辑通用、用户黑模板
     * @param balckTemplatedto
     * @return
     */
    R editBalckTemplate(JsmsBalckTemplateDTO balckTemplatedto);

    /**
     * 校验黑模板基本参数
     * @param balckTemplatedto
     * @return
     */
    R checkBalckTemplate(JsmsBalckTemplateDTO balckTemplatedto);

    /**
     * 校验是否合法，已存在相同黑模板
     * @param balckTemplatedto
     * @return
     */
    R checkBalckTemplate4Rightful(JsmsBalckTemplateDTO balckTemplatedto);

    /**
     * 提供给不同平台的黑模板校验接口
     * @param balckTemplatedto
     * @return
     */
    R checkBalckTemplate4AutoTemplate(JsmsBalckTemplateDTO balckTemplatedto,Integer webid);
}
