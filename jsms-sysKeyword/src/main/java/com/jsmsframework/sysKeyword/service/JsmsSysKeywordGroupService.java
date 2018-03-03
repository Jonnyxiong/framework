package com.jsmsframework.sysKeyword.service;

import java.util.Map;
import java.util.List;
import java.util.Set;

import com.jsmsframework.common.util.PageExportQualifier;
import com.jsmsframework.sysKeyword.dto.JsmsSysKeywordGroupDTO;
import com.jsmsframework.sysKeyword.entity.JsmsSysKeywordGroup;

import com.jsmsframework.common.dto.Result;
import com.jsmsframework.common.dto.JsmsPage;
import org.apache.ibatis.annotations.Param;

/**
 * @description 系统关键字分组表
 * @author huangwenjie
 * @date 2018-01-15
 */
public interface JsmsSysKeywordGroupService {

    int insert(JsmsSysKeywordGroup model);
    
    int insertBatch(List<JsmsSysKeywordGroup> modelList);

    int update(JsmsSysKeywordGroup model);
    
    int updateSelective(JsmsSysKeywordGroup model);

    JsmsSysKeywordGroup getByKgroupId(Integer kgroupId);
    
    JsmsPage queryList(JsmsPage page);

    List<JsmsSysKeywordGroup> findList(Map params);

    int count(Map<String,Object> params);


    /**
     * 查询是否id不同但组名相同的分组
     * @param kgroupName
     * @param kgroupId
     * @return
     */
    int checkKgroupName(String kgroupName,Integer kgroupId);

    int deteleByKgroupId(Integer kgroupId);

    List<JsmsSysKeywordGroup> getByGroupIds(Set<Integer> groupIds);

    JsmsSysKeywordGroup getDefault();
}
