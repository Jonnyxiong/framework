package com.jsmsframework.sysKeyword.service;

import java.util.Map;
import java.util.List;

import com.jsmsframework.sysKeyword.entity.JsmsSysCgroupRefClient;

import com.jsmsframework.common.dto.Result;
import com.jsmsframework.common.dto.JsmsPage;
import org.apache.ibatis.annotations.Param;

/**
 * @description 系统用户组内用户表
 * @author huangwenjie
 * @date 2018-01-15
 */
public interface JsmsSysCgroupRefClientService {

    int insert(JsmsSysCgroupRefClient model);
    
    int insertBatch(List<JsmsSysCgroupRefClient> modelList);

    int update(JsmsSysCgroupRefClient model);
    
    int updateSelective(JsmsSysCgroupRefClient model);

    JsmsSysCgroupRefClient getById(Integer id);
    
    JsmsPage queryList(JsmsPage page);

    List<JsmsSysCgroupRefClient> findList(Map params);

    int count(Map<String,Object> params);

    int deleteByCgroupId(Integer cgroupId);

    List<JsmsSysCgroupRefClient> selectByCgroupId(Integer cgroupId);

    /**
     * 通过用户id查找用户所属的系统关键字用户组（一个用户只对应一个用户组）
     * @param clientId
     * @return
     */
    JsmsSysCgroupRefClient getByClientId(String clientId);
}
