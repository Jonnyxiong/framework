package com.jsmsframework.user.audit.service;

import com.jsmsframework.audit.dto.JsmsBalckTemplateDTO;
import com.jsmsframework.audit.entity.JsmsAutoBlackTemplate;
import com.jsmsframework.audit.entity.JsmsAutoTemplate;
import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.dto.R;
import com.jsmsframework.common.enums.AutoTemplateLevel;
import com.jsmsframework.common.enums.balckAndWhiteTemplate.TemplateLevel;

import java.io.File;


public interface JsmsUserAutoTemplateService {


    R addAutoTemplateBatch(File uploadFile, String uploadContentType, String adminId, String tempFileSavePath,Integer webId);

    R checkAutoTemplate(JsmsAutoTemplate autoTemplate,String adminId);

    @Deprecated
    JsmsPage findListOfAutoTemplate(JsmsPage<JsmsAutoTemplate> page, Integer webid, AutoTemplateLevel autoTemplateLevel);

    JsmsPage findListOfAutoTemplate(JsmsPage<JsmsAutoTemplate> page, Integer webid, TemplateLevel templateLevel);

    JsmsPage findListOfAutoTemplate(JsmsPage<JsmsAutoTemplate> page, Integer webid);

}
