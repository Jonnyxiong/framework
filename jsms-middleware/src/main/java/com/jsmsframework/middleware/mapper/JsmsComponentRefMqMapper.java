package com.jsmsframework.middleware.mapper;

import com.jsmsframework.middleware.dto.ComponentRefMqDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import com.jsmsframework.middleware.entity.JsmsComponentRefMq;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 组件与MQ关联表
 * @author huangwenjie
 * @date 2017-11-04
 */
@Repository
public interface JsmsComponentRefMqMapper{

	int insert(JsmsComponentRefMq model);
	
	int insertBatch(List<JsmsComponentRefMq> modelList);

	
	int update(JsmsComponentRefMq model);
	
	int updateSelective(JsmsComponentRefMq model);

    JsmsComponentRefMq getById(Integer id);

	List<JsmsComponentRefMq> queryList(JsmsPage<JsmsComponentRefMq> page);

	int count(Map<String,Object> params);

	List<JsmsComponentRefMq> queryList1(JsmsPage<JsmsComponentRefMq> page);

	int count1(Map<String,Object> params);


	List<ComponentRefMqDTO> queryByParam(Map<String,Object> params);

	ComponentRefMqDTO queryByCMM(@Param("componentId") Integer componentId, @Param("messageType") String messageType, @Param("mode") Integer mode);
}