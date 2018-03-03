package com.jsmsframework.user.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.user.entity.JsmsClientInfoExt;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description 用户信息扩展表
 * @author lpjLiu
 * @date 2017-09-25
 */
public interface JsmsClientInfoExtService {

	int insert(JsmsClientInfoExt model);

	int insertBatch(List<JsmsClientInfoExt> modelList);

	int update(JsmsClientInfoExt model);

	int updateSelective(JsmsClientInfoExt model);

	JsmsClientInfoExt getByClientId(String clientid);

	List<JsmsClientInfoExt> getByClientIds(Set<String> clientIds);

	JsmsPage queryList(JsmsPage page);

	int count(Map<String, Object> params);

	List<JsmsClientInfoExt> findList(JsmsClientInfoExt model);

	List<JsmsClientInfoExt> getAuditKeyAccountInfoExt();

	int updateByClientIdOfWeb(JsmsClientInfoExt model);
}
