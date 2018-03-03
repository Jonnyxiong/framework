package com.jsmsframework.common.mapper;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.entity.JsmsAgentClientParam;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @description 代理商和客户属性配置表
 * @author huangwenjie
 * @date 2017-08-09
 */
@Repository
public interface JsmsAgentClientParamMapper{

	int insert(JsmsAgentClientParam model);
	
	int insertBatch(List<JsmsAgentClientParam> modelList);

	
	int update(JsmsAgentClientParam model);
	
	int updateSelective(JsmsAgentClientParam model);

    JsmsAgentClientParam getByParamId(Long paramId);

	List<JsmsAgentClientParam> queryList(JsmsPage<JsmsAgentClientParam> page);

	int count(Map<String,Object> params);

	JsmsAgentClientParam getByParamKey(String paramKey);
}