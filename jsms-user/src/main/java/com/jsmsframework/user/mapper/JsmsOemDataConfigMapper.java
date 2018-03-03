package com.jsmsframework.user.mapper;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.user.entity.JsmsOemDataConfig;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @description OEM资料配置
 * @author huangwenjie
 * @date 2017-08-16
 */
@Repository
public interface JsmsOemDataConfigMapper{

	int insert(JsmsOemDataConfig model);
	
	int insertBatch(List<JsmsOemDataConfig> modelList);

	
	int update(JsmsOemDataConfig model);
	
	int updateSelective(JsmsOemDataConfig model);

    JsmsOemDataConfig getById(Integer id);

    JsmsOemDataConfig getByAgentId(Integer agentId);

	List<JsmsOemDataConfig> queryList(JsmsPage<JsmsOemDataConfig> page);

	int count(Map<String,Object> params);

	int updateSelectiveByAgentId(JsmsOemDataConfig model);
	//根据代理商id获取测试短信id和赠送测试短信条数
	public JsmsOemDataConfig getOemDataConfig(@Param("agentId") Integer agentId);
}