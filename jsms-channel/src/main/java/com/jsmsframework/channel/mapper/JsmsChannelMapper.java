package com.jsmsframework.channel.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jsmsframework.channel.entity.JsmsChannel;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 短信通道信息表
 * @author huangwenjie
 * @date 2017-10-12
 */
@Repository
public interface JsmsChannelMapper{

	int insert(JsmsChannel model);
	
	int insertBatch(List<JsmsChannel> modelList);

	
	int update(JsmsChannel model);
	
	int updateSelective(JsmsChannel model);

    JsmsChannel getById(Integer id);

	List<JsmsChannel> queryList(JsmsPage<JsmsChannel> page);

	int count(Map<String,Object> params);

	List<JsmsChannel> queryAll();

	List<JsmsChannel> queryAllByOperatorstype(@Param("operatorstype") Integer operatorstype);

	JsmsChannel getByCid(@Param("cid")Integer cid);

	List<JsmsChannel> getByCids(@Param("cids") Set<Integer> cids);
}