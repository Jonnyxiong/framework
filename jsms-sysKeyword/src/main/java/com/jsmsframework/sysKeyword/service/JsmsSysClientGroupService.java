package com.jsmsframework.sysKeyword.service;

import java.util.Map;
import java.util.List;

import com.jsmsframework.common.util.PageExportQualifier;
import com.jsmsframework.sysKeyword.entity.JsmsSysClientGroup;

import com.jsmsframework.common.dto.Result;
import com.jsmsframework.common.dto.JsmsPage;
import org.apache.ibatis.annotations.Param;

/**
 * @description 系统用户组
 * @author huangwenjie
 * @date 2018-01-15
 */
public interface JsmsSysClientGroupService {

    int insert(JsmsSysClientGroup model);
    
    int insertBatch(List<JsmsSysClientGroup> modelList);

    int update(JsmsSysClientGroup model);
    
    int updateSelective(JsmsSysClientGroup model);

    JsmsSysClientGroup getByCgroupId(Integer cgroupId);
    
    JsmsPage queryList(JsmsPage page);

    List<JsmsSysClientGroup> findList(Map params);

    int count(Map<String,Object> params);

    /**
     * 通过其他表关联查询
     * @param jsmsPage
     * @return
     */
    JsmsPage queryListByOtherTable(JsmsPage jsmsPage);

    /**
     * 统计用户组名相同的其他数据
     * @param cGruopName
     * @param cGroupId
     * @return
     */
    int checkByCGroupName(String cGruopName,Integer cGroupId);

    int deleteByCgroupId(Integer cgroupId);

    /**
     * 获取默认用户组
     * @return
     */
    JsmsSysClientGroup getDefault();
}
