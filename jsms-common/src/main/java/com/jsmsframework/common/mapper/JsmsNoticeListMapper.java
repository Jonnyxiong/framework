package com.jsmsframework.common.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import com.jsmsframework.common.entity.JsmsNoticeList;
import com.jsmsframework.common.dto.JsmsPage;

/**
 * @description 公告管理表
 * @author huangwenjie
 * @date 2017-12-06
 */
@Repository
public interface JsmsNoticeListMapper {

	int insert(JsmsNoticeList model);

	int insertBatch(List<JsmsNoticeList> modelList);


	int update(JsmsNoticeList model);

	int updateSelective(JsmsNoticeList model);

    JsmsNoticeList getById(Integer id);

	List<JsmsNoticeList> queryList(JsmsPage<JsmsNoticeList> page);

	List<JsmsNoticeList> queryListAll(Map<String, Object> params);

	JsmsNoticeList getContentById(Integer id);

	int count(Map<String, Object> params);

	//更改表数据的状态为已发布
	public int updateStatus(@Param("status") Integer status,@Param("id") Integer id);

}