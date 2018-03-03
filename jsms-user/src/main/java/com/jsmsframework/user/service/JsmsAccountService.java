package com.jsmsframework.user.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.user.entity.JsmsAccount;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description 用户信息表
 * @author huangwenjie
 * @date 2017-08-09
 */
public interface JsmsAccountService {

    int insert(JsmsAccount model);
    
    int insertBatch(List<JsmsAccount> modelList);

    int update(JsmsAccount model);

    int updateByClientId(JsmsAccount model);

    int updateSelective(JsmsAccount model);

    JsmsAccount getById(String id);

    JsmsAccount getByClientId(String clientid);

    List<JsmsAccount> getByIds(Set<String> clientIds);

    JsmsPage queryList(JsmsPage page);

    List<JsmsAccount> queryAll(Map params);

    List<JsmsAccount> getLikeIdAndName(Map params);

    List<JsmsAccount> findAllList(String clientId);

    int count(Map<String,Object> params);

    int updateBelongSale(JsmsAccount model);

    List<JsmsAccount> findAllListOfOperation(Long belongSale);

    List<JsmsAccount> findListForReturnQuantity(Integer agentId);

    List<Map<String, Object>> findALLAccountForSearch();

    List<JsmsAccount> getAllAccount(String leftContent);
    List<JsmsAccount> getByName(String name);

    List<JsmsAccount> getAllAccountOfExist(Integer cgroupId,String rightContent);

    List<JsmsAccount> findList(@Param("model") JsmsAccount model, @Param("agentIds") Set agentIds);

    List<JsmsAccount> findAllListExist(String clientId,String type);

    boolean checkAccountIsCancel(String clientId);


    /**
     * 根据代理商类型（webId）加载所有的子账户
     * @param webId
     * @return
     */
    List<JsmsAccount> loadAllForSelect(@Param("webId") String webId);

    /**
     * 查找没有分配系统关键字用户组的用户
     * @param leftContent
     * @return
     */
    List<JsmsAccount> getAllAccountWithoutSysClientGroup(String leftContent);

    /**
     * 查找已经某个系统关键字用户组的所有用户
     * @param cgroupId
     * @param rightContent
     * @return
     */
    List<JsmsAccount> getAccountOfSysClientGroup(Integer cgroupId,String rightContent);
}
