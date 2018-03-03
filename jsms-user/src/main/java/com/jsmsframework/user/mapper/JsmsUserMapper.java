package com.jsmsframework.user.mapper;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.user.entity.JsmsUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description 用户表
 * @author NiuT
 * @date 2017-08-10
 */
@Repository
public interface JsmsUserMapper{

	int insert(JsmsUser model);
	
	int insertBatch(List<JsmsUser> modelList);

	
	int update(JsmsUser model);
	
	int updateSelective(JsmsUser model);

    JsmsUser getById(String id);

	JsmsUser getByIdByOld(Long id);

	List<JsmsUser> getByIds(@Param("ids") Set<Long> id);

	List<JsmsUser> queryList(JsmsPage<JsmsUser> page);

	int count(Map<String,Object> params);

	List<JsmsUser> getByRealname(String realname);

	JsmsUser checkOemEmailAndMobileInUser(@Param("email") String email, @Param("mobile") String mobile);

	JsmsUser agentApplyCheckInUser(@Param("email") String email, @Param("mobile") String mobile);

	List<JsmsUser> getSaleInfoByUserId(Map<String,Object> params);
	
	List<JsmsUser> getUserByRoleName(String roleName);

	List<JsmsUser> queryAll();

	Long getBelongSale(String realName);

	Integer querySmsUserCountByEmail(String email);

	Integer querySmsUserCountByMobile(String mobile);

	Long getId(@Param("email") String email, @Param("mobile") String mobile);

}