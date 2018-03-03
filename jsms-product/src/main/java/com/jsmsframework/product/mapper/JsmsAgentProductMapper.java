package com.jsmsframework.product.mapper;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.product.entity.JsmsAgentProduct;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description 代理商产品表
 * @author huangwenjie
 * @date 2017-08-16
 */
@Repository
public interface JsmsAgentProductMapper{

	int insert(JsmsAgentProduct model);
	
	int insertBatch(List<JsmsAgentProduct> modelList);

	
	int update(JsmsAgentProduct model);
	
	int updateSelective(JsmsAgentProduct model);

    JsmsAgentProduct getById(Integer id);

	List<JsmsAgentProduct> getByAgentId(Integer agentId);

	List<JsmsAgentProduct> getByAgentIds(@Param("agentIds") Set<Integer> agentIds);

	List<JsmsAgentProduct> queryList(JsmsPage<JsmsAgentProduct> page);

	int count(Map<String,Object> params);

}