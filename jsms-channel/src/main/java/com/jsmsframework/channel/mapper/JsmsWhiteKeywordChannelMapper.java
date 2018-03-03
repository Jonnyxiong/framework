package com.jsmsframework.channel.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import com.jsmsframework.channel.entity.JsmsWhiteKeywordChannel;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 白关键字强制路由通道表
 * @author huangwenjie
 * @date 2017-09-20
 */
@Repository
public interface JsmsWhiteKeywordChannelMapper{

	int insert(JsmsWhiteKeywordChannel model);
	
	int insertBatch(List<JsmsWhiteKeywordChannel> modelList);

	
	int update(JsmsWhiteKeywordChannel model);
	
	int updateSelective(JsmsWhiteKeywordChannel model);

    JsmsWhiteKeywordChannel getById(Integer id);

	List<JsmsWhiteKeywordChannel> queryList(JsmsPage<JsmsWhiteKeywordChannel> page);

	int count(Map<String,Object> params);

	int delete(Integer id);

	int isExist(JsmsWhiteKeywordChannel model);
	List<String> getAllWhiteKeywordList();
}