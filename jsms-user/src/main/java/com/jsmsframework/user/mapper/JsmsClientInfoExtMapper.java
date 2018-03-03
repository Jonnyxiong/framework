package com.jsmsframework.user.mapper;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.user.entity.JsmsClientInfoExt;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description 用户信息扩展表
 * @author lpjLiu
 * @date 2017-09-25
 */
@Repository
public interface JsmsClientInfoExtMapper {

	int insert(JsmsClientInfoExt model);

	int insertBatch(List<JsmsClientInfoExt> modelList);

	int update(JsmsClientInfoExt model);

	int updateSelective(JsmsClientInfoExt model);

	JsmsClientInfoExt getByClientId(String clientid);

	List<JsmsClientInfoExt> getByClientIds(@Param("clientIds") Set<String> clientIds);

	List<JsmsClientInfoExt> queryList(JsmsPage<JsmsClientInfoExt> page);

	int count(Map<String, Object> params);

	List<JsmsClientInfoExt> findList(JsmsClientInfoExt model);

	List<JsmsClientInfoExt> getByStarLevels(@Param("starLevels") Set<Integer> starLevels);

	int updateSelectiveOfInfoExt(@Param("webPassword") String webPassword, @Param("clientid") String clientid);
}