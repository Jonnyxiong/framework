package com.jsmsframework.user.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.user.entity.JsmsUser;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description 用户表
 * @author NiuT
 * @date 2017-08-10
 */
public interface JsmsUserService {

    int insert(JsmsUser model);

    int insertBatch(List<JsmsUser> modelList);

    int update(JsmsUser model);

    int updateSelective(JsmsUser model);

    JsmsUser getById(String id);

    JsmsUser getById2(Long id);

    List<JsmsUser> getByIds(Set<Long> ids);

    JsmsPage queryList(JsmsPage page);

    int count(Map<String,Object> params);

    List<JsmsUser> getByRealname(String realname);

    JsmsUser checkOemEmailAndMobileInUser(String email,String mobile);

    JsmsUser agentApplyCheckInUser(String email,String mobile);

    List<JsmsUser> getSaleInfoByUserId(Map<String,Object> params);
	
	List<JsmsUser> getUserByRoleName(String roleName);

    Integer querySmsUserCountByEmail(String email);

    Integer querySmsUserCountByMobile(String mobile);

    Long getId(String email,String mobile);

    List<JsmsUser> queryAll();
}
