package com.jsmsframework.sysKeyword.mapper;

import com.jsmsframework.common.interceptor.SimpleCountSQL;
import com.jsmsframework.sysKeyword.dto.JsmsSysClientGroupDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import com.jsmsframework.sysKeyword.entity.JsmsSysClientGroup;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 系统用户组
 * @author huangwenjie
 * @date 2018-01-15
 */
@Repository
public interface JsmsSysClientGroupMapper{

	int insert(JsmsSysClientGroup model);
	
	int insertBatch(List<JsmsSysClientGroup> modelList);

	
	int update(JsmsSysClientGroup model);
	
	int updateSelective(JsmsSysClientGroup model);

    JsmsSysClientGroup getByCgroupId(Integer cgroupId);

	@SimpleCountSQL
	List<JsmsSysClientGroup> queryList(JsmsPage<JsmsSysClientGroup> page);

	List<JsmsSysClientGroup> findList(Map params);

	int count(Map<String,Object> params);

	List<JsmsSysClientGroupDTO> queryListForDTO(JsmsPage<JsmsSysClientGroup> page);

	int checkByCGroupName(@Param("cGroupName")String cGruopName,@Param("cGroupId") Integer cGroupId);

	int deleteByCgroupId(@Param("cgroupId") Integer cgroupId);

	/**
	 * 获取默认用户组
	 * @return
	 */
	JsmsSysClientGroup getDefault();
}