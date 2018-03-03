package com.jsmsframework.access.access.mapper;

import com.jsmsframework.access.access.entity.JsmsClientFailReturn;
import com.jsmsframework.common.dto.JsmsPage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description 客户失败返回清单表
 * @author huangwenjie
 * @date 2017-10-16
 */
@Repository
public interface JsmsClientFailReturnMapper{

	int insert(JsmsClientFailReturn model);
	
	int insertBatch(List<JsmsClientFailReturn> modelList);

	
	int update(JsmsClientFailReturn model);
	
	int updateSelective(JsmsClientFailReturn model);

	int updateStatus(@Param("refundState") Integer refundState, @Param("idList") List<Integer> idList);

    JsmsClientFailReturn getById(Integer id);

	List<JsmsClientFailReturn> queryList(JsmsPage<JsmsClientFailReturn> page);

	List<JsmsClientFailReturn> getBySubIds(@Param("subIds")Set<String> subIds);

	List<JsmsClientFailReturn> getByIds(@Param("ids")Set<Integer> ids);

	int count(Map<String, Object> params);

    List<JsmsClientFailReturn> queryAll(Map<String, Object> params);
}