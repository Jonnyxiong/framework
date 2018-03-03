package com.jsmsframework.user.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import com.jsmsframework.user.entity.JsmsOauthPic;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 代理商和客户证件表
 * @author huangwenjie
 * @date 2017-08-16
 */
@Repository
public interface JsmsOauthPicMapper{

	int insert(JsmsOauthPic model);
	
	int insertBatch(List<JsmsOauthPic> modelList);

	
	int update(JsmsOauthPic model);
	
	int updateSelective(JsmsOauthPic model);

    JsmsOauthPic getById(Integer id);

	List<JsmsOauthPic> queryList(JsmsPage<JsmsOauthPic> page);

	int count(Map<String,Object> params);

}