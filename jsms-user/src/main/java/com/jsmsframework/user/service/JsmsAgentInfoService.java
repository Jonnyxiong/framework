package com.jsmsframework.user.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.user.entity.JsmsAgentInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description 代理商信息表
 * @author huangwenjie
 * @date 2017-08-09
 */
public interface JsmsAgentInfoService {

    int insert(JsmsAgentInfo model);
    
    int insertBatch(List<JsmsAgentInfo> modelList);

    int update(JsmsAgentInfo model);
    
    int updateSelective(JsmsAgentInfo model);

    JsmsAgentInfo getByAgentId(Integer agentId);

    JsmsAgentInfo getByAdminId(Long adminId);

    List<JsmsAgentInfo> getByAgentIds(Set agentId);

    List<JsmsAgentInfo> getByAgentIdsAndType(Integer agentType, Set agentId);

    JsmsPage queryList(JsmsPage page);

    List<JsmsAgentInfo> findList(JsmsAgentInfo model);

    int count(Map<String,Object> params);

    List<JsmsAgentInfo> getByAgentName(String agentName);

    JsmsPage queryListForSale(JsmsPage page);

    List<Integer> queryAgentIdsByParams(Map<String,Object> params);

    List<JsmsAgentInfo> queryAll(Map<String,Object> params);


    List<JsmsAgentInfo> getAgentInfoNotInInvoiceAuth(JsmsPage<JsmsAgentInfo> page);

    List<JsmsAgentInfo> getByBelongSale(Long  belongSale);

    // 根据权限获取
    List<JsmsAgentInfo> findListByRight(Map<String, Object> params);

    /**
     * 根据代理商类型（webId）加载所有的子账户
     * @param webId
     * @return
     */
    List<JsmsAgentInfo> loadAllForSelect(@Param("webId") String webId);
}
