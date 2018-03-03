package com.jsmsframework.sysKeyword.mapper;

import com.jsmsframework.common.interceptor.SimpleCountSQL;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import com.jsmsframework.sysKeyword.entity.JsmsSysCgroupRefClient;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 系统用户组内用户表
 * @author huangwenjie
 * @date 2018-01-15
 */
@Repository
public interface JsmsSysCgroupRefClientMapper{

	int insert(JsmsSysCgroupRefClient model);
	
	int insertBatch(List<JsmsSysCgroupRefClient> modelList);

	
	int update(JsmsSysCgroupRefClient model);
	
	int updateSelective(JsmsSysCgroupRefClient model);

    JsmsSysCgroupRefClient getById(Integer id);

	@SimpleCountSQL
	List<JsmsSysCgroupRefClient> queryList(JsmsPage<JsmsSysCgroupRefClient> page);

	List<JsmsSysCgroupRefClient> findList(Map params);

	int count(Map<String,Object> params);

	int deleteByCgroupId(@Param("cgroupId") Integer cgroupId);

	List<JsmsSysCgroupRefClient> selectByCgroupId(@Param("cgroupId") Integer cgroupId);

	/**
	 * 通过用户id查找用户所属的系统关键字用户组
	 * @param clientId
	 * @return
	 */
	JsmsSysCgroupRefClient getByClientId(@Param("clientId") String clientId);
}