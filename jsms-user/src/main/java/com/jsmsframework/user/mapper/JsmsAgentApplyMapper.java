package com.jsmsframework.user.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import com.jsmsframework.user.entity.JsmsAgentApply;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 代理商申请表
 * @author huangwenjie
 * @date 2017-08-16
 */
@Repository
public interface JsmsAgentApplyMapper{

	int insert(JsmsAgentApply model);
	
	int insertBatch(List<JsmsAgentApply> modelList);

	
	int update(JsmsAgentApply model);
	
	int updateSelective(JsmsAgentApply model);

    JsmsAgentApply getById(Integer id);

	List<JsmsAgentApply> queryList(JsmsPage<JsmsAgentApply> page);

	int count(Map<String,Object> params);

	JsmsAgentApply checkEmailAndMobileInApply(@Param("email") String email, @Param("mobile") String mobile);

	Integer getIdByEmailAndMobile(@Param("email") String email, @Param("mobile") String mobile);

}