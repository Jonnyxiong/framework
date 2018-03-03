package com.jsmsframework.middleware.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jsmsframework.middleware.entity.JsmsComponentConfigure;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 组件配置信息表
 * @author huangwenjie
 * @date 2017-11-04
 */
@Repository
public interface JsmsComponentConfigureMapper{

	int insert(JsmsComponentConfigure model);
	
	int insertBatch(List<JsmsComponentConfigure> modelList);

	
	int update(JsmsComponentConfigure model);
	
	int updateSelective(JsmsComponentConfigure model);

    JsmsComponentConfigure getById(Integer id);

	List<JsmsComponentConfigure> queryList(JsmsPage<JsmsComponentConfigure> page);

	int count(Map<String,Object> params);

	int updateSwitch(@Param("ids") Set<Integer> ids,@Param("comswitch") Integer comswitch);


	/**
	 * @Description  根据组件类型加载对应的所有组件
	 * @author yeshiyuan
	 * @date 2018/1/8 17:58
	 * @return java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
	 */
	List<JsmsComponentConfigure> loadAllForSelectByType(List<String> list);
}