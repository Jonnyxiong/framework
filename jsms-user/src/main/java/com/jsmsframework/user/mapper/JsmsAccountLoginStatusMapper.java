package com.jsmsframework.user.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import com.jsmsframework.user.entity.JsmsAccountLoginStatus;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 用户登陆状态表
 * @author huangwenjie
 * @date 2017-08-16
 */
@Repository
public interface JsmsAccountLoginStatusMapper{

	int insert(JsmsAccountLoginStatus model);
	
	int insertBatch(List<JsmsAccountLoginStatus> modelList);

	
	int update(JsmsAccountLoginStatus model);
	
	int updateSelective(JsmsAccountLoginStatus model);

    JsmsAccountLoginStatus getById(Integer id);

	List<JsmsAccountLoginStatus> queryList(JsmsPage<JsmsAccountLoginStatus> page);

	int count(Map<String,Object> params);


}