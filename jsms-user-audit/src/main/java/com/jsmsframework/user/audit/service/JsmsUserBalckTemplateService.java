package com.jsmsframework.user.audit.service;

import com.jsmsframework.audit.entity.JsmsAutoBlackTemplate;
import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.dto.R;

import java.io.File;

/**
 * Created by Don on 2017/12/8.
 */
public interface JsmsUserBalckTemplateService {

    JsmsPage findListOfBalckTemplate(JsmsPage<JsmsAutoBlackTemplate> page);

    R addBalckTemplateBatch(File uploadFile, String uploadContentType, String adminId, String tempFileSavePath);
}
