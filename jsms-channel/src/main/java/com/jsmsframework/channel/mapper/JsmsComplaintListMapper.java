package com.jsmsframework.channel.mapper;

import com.jsmsframework.channel.entity.JsmsComplaintList;
import com.jsmsframework.channel.entity.JsmsComplaintListExt;
import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.interceptor.SimpleCountSQL;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @description 投诉明细记录表
 * @author huangwenjie
 * @date 2018-01-09
 */
@Repository
public interface JsmsComplaintListMapper{

	int insert(JsmsComplaintList model);
	
	int insertBatch(List<JsmsComplaintList> modelList);

	
	int update(JsmsComplaintList model);
	
	int updateSelective(JsmsComplaintList model);

    JsmsComplaintList getById(Integer id);

	@SimpleCountSQL
	List<JsmsComplaintList> queryList(JsmsPage<JsmsComplaintList> page);

	List<JsmsComplaintList> queryListExt(JsmsPage<JsmsComplaintList> page);

	List<JsmsComplaintList> findList(Map params);

	int count(Map<String, Object> params);

	//根据id删除投诉
	int deleteById(Integer id);
	//分页搜索投诉
	List<JsmsComplaintListExt> searchComplaint(JsmsPage page);

	List<JsmsComplaintList> findListGroup(Map params);

	List<JsmsComplaintList> getDuplicateData(Map params);
	int count4Channel(Map<String, Object> params);
	//投诉搜索的求总数
	int countComplaint(Map<String, Object> params);

}