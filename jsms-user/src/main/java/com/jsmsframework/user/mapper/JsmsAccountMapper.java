package com.jsmsframework.user.mapper;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.user.entity.JsmsAccount;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description 用户信息表
 * @author huangwenjie
 * @date 2017-08-09
 */
@Repository
public interface JsmsAccountMapper {

	int insert(JsmsAccount model);

	int insertBatch(List<JsmsAccount> modelList);

	int update(JsmsAccount model);

	int updateSelective(JsmsAccount model);

	JsmsAccount getById(String id);

	JsmsAccount getByClientId(String clientid);

	List<JsmsAccount> getByIds(@Param("clientIds") Set<String> clientIds);

	List<JsmsAccount> queryList(JsmsPage<JsmsAccount> page);

	List<JsmsAccount> queryAll(Map params);

//	List<JsmsAccount> getLikeIdAndName(@Param("clientId") String clientId,@Param("clientName") String name);
	List<JsmsAccount> getLikeIdAndName(Map params);

	List<JsmsAccount> findAllList(@Param("clientId") String clientId);

	int count(Map<String, Object> params);

	int updateBelongSale(JsmsAccount model);

	List<JsmsAccount> findAllListOfOperation(@Param("belongSale") Long belongSale);

	List<JsmsAccount> findListForReturnQuantity(@Param("agentId") Integer agentId);

	List<Map<String, Object>> findALLAccountForSearch();

	/*JsmsInfoExt getByClientIdOfInfoExt(String clientid);*/
	List<JsmsAccount> getAllAccount(@Param("leftContent")String leftContent);

	List<JsmsAccount> getAllAccountOfExist(@Param("cgroupId") Integer cgroupId,@Param("rightContent") String rightContent);
	List<JsmsAccount> getByName(String name);

	List<JsmsAccount> findList(@Param("params") JsmsAccount params, @Param("agentIds") Set agentIds);

	List<JsmsAccount> findAllListExist(@Param("clientId") String clientId,@Param("type") String type);

	int checkAccountIsCancel(@Param("clientId")String clientId);

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
	List<JsmsAccount> getAllAccountWithoutSysClientGroup(@Param("leftContent")String leftContent);

	/**
	 * 查找已经某个系统关键字用户组的所有用户
	 * @param cgroupId
	 * @param rightContent
	 * @return
	 */
	List<JsmsAccount> getAccountOfSysClientGroup(@Param("cgroupId") Integer cgroupId,@Param("rightContent") String rightContent);
}