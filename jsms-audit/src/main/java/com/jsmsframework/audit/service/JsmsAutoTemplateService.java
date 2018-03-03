package com.jsmsframework.audit.service;

import com.jsmsframework.audit.dto.JsmsAutoTemplateDTO;
import com.jsmsframework.audit.entity.JsmsAutoTemplate;
import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.dto.R;
import org.springframework.context.annotation.Description;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @description 智能模板表
 * @author huangwenjie
 * @date 2017-08-28
 */
public interface JsmsAutoTemplateService {

    int insert(JsmsAutoTemplate model);
    
    int insertBatch(List<JsmsAutoTemplate> modelList);

    int update(JsmsAutoTemplate model);
    
    int updateSelective(JsmsAutoTemplate model);

    JsmsAutoTemplate getByTemplateId(Integer templateId);

    JsmsPage queryList(JsmsPage page);

    JsmsPage findList(JsmsPage page);

    int count(Map<String,Object> params);

    R checkAutoTemplate(JsmsAutoTemplate autoTemplate);

    R checkAutoTemplateAudit(JsmsAutoTemplate autoTemplate);

    R addAutoTemplate(JsmsAutoTemplate autoTemplate);

    @Deprecated
    R addAutoTemplateBatch(File uploadFile, String uploadContentType, Long adminId, String tempFileSavePath);

    R delAutoTemplate(Integer templateId);

    R modAutoTemplate(JsmsAutoTemplate autoTemplate,boolean isSureDel);

    @Deprecated
    @Description("推荐使用queryPageList(JsmsPage<JsmsAutoTemplate> page)")
    JsmsPage findListOfOperation(JsmsPage<JsmsAutoTemplate> page);

    JsmsPage queryPageList(JsmsPage<JsmsAutoTemplate> page);

    R modifyTemplate(JsmsAutoTemplate autoTemplate);

    R checkModifTemplate(JsmsAutoTemplate autoTemplate);

    boolean isExistAutoTemplate(JsmsAutoTemplateDTO autoTemplate);

    int countOfOperation(Map<String,String> params);

	Map<String, Object> getAuditNum();

    /**
     * 智能模板校验智能通用、用户模板是否存在合法
     * @param autoTemplate
     * @param isSureDel
     * @return
     */
	R checkAutoTemplateRightful(JsmsAutoTemplate autoTemplate,Boolean isSureDel);

    /**
     * 添加通用智能模板
     * @param autoTemplate 智能模板实体
     * @param isSureDel 删除标志
     * @return
     */
    R addAutoTemplate(JsmsAutoTemplate autoTemplate,boolean isSureDel);
}
