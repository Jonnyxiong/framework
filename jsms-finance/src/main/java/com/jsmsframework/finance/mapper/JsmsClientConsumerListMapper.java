package com.jsmsframework.finance.mapper;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.finance.entity.JsmsClientConsumerList;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @description 品牌客户消耗记录
 * @author lpjLiu
 * @date 2017-10-11
 */
@Repository
public interface JsmsClientConsumerListMapper {

	int insert(JsmsClientConsumerList model);

	int insertBatch(List<JsmsClientConsumerList> modelList);

	int update(JsmsClientConsumerList model);

	int updateSelective(JsmsClientConsumerList model);

	JsmsClientConsumerList getById(Integer id);

	List<JsmsClientConsumerList> queryList(JsmsPage<JsmsClientConsumerList> page);

	int count(Map<String, Object> params);

	/**
	  * @Description: 品牌客户消耗记录,短信总数
	  * @Author: tanjiangqiang
	  * @Date: 2018/1/4 - 15:02
	  * @param params
	  * @return
	  *
	  */
	Integer queryBrandConsumeTotal(Map params);

}