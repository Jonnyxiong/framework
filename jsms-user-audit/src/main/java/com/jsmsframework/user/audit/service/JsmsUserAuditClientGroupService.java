package com.jsmsframework.user.audit.service;

import com.jsmsframework.audit.entity.JsmsAuditClientGroup;
import com.jsmsframework.common.dto.JsmsPage;

import java.util.Map;

/**
 * Created by xiongfenglin on 2017/10/31.
 *
 * @author: xiongfenglin
 */
public interface JsmsUserAuditClientGroupService {
    JsmsPage queryList(JsmsPage page);

    int configurationkeyword(Map<String, String> params,JsmsAuditClientGroup jsmsAuditClientGroup);
}
