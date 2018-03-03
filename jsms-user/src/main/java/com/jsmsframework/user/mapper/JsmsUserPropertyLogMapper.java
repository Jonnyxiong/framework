package com.jsmsframework.user.mapper;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.user.entity.JsmsUserPropertyLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @description 用户属性变更记录
 * @author lpjLiu
 * @date 2017-10-11
 */
@Repository
public interface JsmsUserPropertyLogMapper{

	int insert(JsmsUserPropertyLog model);
	
	int insertBatch(List<JsmsUserPropertyLog> modelList);

	
	int update(JsmsUserPropertyLog model);
	
	int updateSelective(JsmsUserPropertyLog model);

    JsmsUserPropertyLog getById(Integer id);

	List<JsmsUserPropertyLog> queryList(JsmsPage<JsmsUserPropertyLog> page);

	int count(Map<String,Object> params);

	/**
	 * 获取属性值
	 * @param clientId
	 * @param date 格式如 20171011
	 * @return
	 */
	Integer getChargeRuleByClientIdAndDate(@Param("clientId") String clientId, @Param("date") String date);

	/**
	 * 获取是否可以更新的一条记录
	 * @param clientId
	 * @param date
	 * @return
	 */
	JsmsUserPropertyLog getCanUpdateChargeRuleByClientIdAndEffectDate(@Param("clientId") String clientId, @Param("date") String date);

	/**
	 * 查询最后生效的里列表
	 * @return
	 */
	List<JsmsUserPropertyLog> findLastEffectDateList(@Param("property") String property, @Param("value") String value);

	/**
	 * @param property 属性值
	 * @param clientid 客户id
	 * @Description: 根据客户id和属性值查询该用户属性配置(clientid和property为联合主键)
	 * @Author: tanjiangqiang
	 * @Date: 2018/1/2 - 19:55
	 */
	JsmsUserPropertyLog getUserPropertyByClientidAndProperty(@Param("property") String property, @Param("clientid") String clientid);

	/**
	  * @Description: 根据客户id和属性值更新用户属性配置
	  * @Author: tanjiangqiang
	  * @Date: 2018/1/2 - 20:48
	  * @param model
	  *
	  */
	int updateValueByClientidAndProperty(JsmsUserPropertyLog model);
}