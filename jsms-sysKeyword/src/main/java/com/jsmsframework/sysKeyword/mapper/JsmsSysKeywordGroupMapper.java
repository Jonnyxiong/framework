package com.jsmsframework.sysKeyword.mapper;

import com.jsmsframework.common.interceptor.SimpleCountSQL;
import com.jsmsframework.sysKeyword.dto.JsmsSysKeywordGroupDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jsmsframework.sysKeyword.entity.JsmsSysKeywordGroup;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 系统关键字分组表
 * @author huangwenjie
 * @date 2018-01-15
 */
@Repository
public interface JsmsSysKeywordGroupMapper{

	int insert(JsmsSysKeywordGroup model);
	
	int insertBatch(List<JsmsSysKeywordGroup> modelList);

	
	int update(JsmsSysKeywordGroup model);
	
	int updateSelective(JsmsSysKeywordGroup model);

    JsmsSysKeywordGroup getByKgroupId(Integer kgroupId);

	@SimpleCountSQL
	List<JsmsSysKeywordGroup> queryList(JsmsPage<JsmsSysKeywordGroup> page);

	List<JsmsSysKeywordGroup> findList(Map params);

	int count(Map<String,Object> params);

	List<JsmsSysKeywordGroupDTO> queryListForDTO(JsmsPage<JsmsSysKeywordGroupDTO> jsmsPage);

	/**
	 * 查看不是此id但是相同的组别名字
	 * @param kgroupName
	 * @param kgroupId
	 * @return
	 */
	int checkKgroupName(@Param(value="kgroupName") String kgroupName, @Param(value="kgroupId") Integer kgroupId);

	/**
	 * 通过id删除
	 * @param kgroupId
	 * @return
	 */
	int deteleByKgroupId(@Param(value = "kgroupId") Integer kgroupId);

	List<JsmsSysKeywordGroup> getByGroupIds(@Param(value = "groupIds") Set<Integer> groupIds);

	JsmsSysKeywordGroup getDefault();
}