package com.jsmsframework.stats.mapper;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.stats.entity.JsmsClientSuccessRateRealtime;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @description 用户成功率数据统计表
 * @author huangwenjie
 * @date 2017-11-28
 */
@Repository
public interface JsmsClientSuccessRateRealtimeMapper{

	int insert(JsmsClientSuccessRateRealtime model);
	
	int insertBatch(List<JsmsClientSuccessRateRealtime> modelList);

	
	int update(JsmsClientSuccessRateRealtime model);
	
	int updateSelective(JsmsClientSuccessRateRealtime model);

    JsmsClientSuccessRateRealtime getById(Long id);

	List<JsmsClientSuccessRateRealtime> queryList(JsmsPage<JsmsClientSuccessRateRealtime> page);

	int count(Map<String, Object> params);

    List<JsmsClientSuccessRateRealtime> queryListByMap(Map params);

	List<JsmsClientSuccessRateRealtime> getLastOneStats(@Param("clientIds") List<String> clientIds,@Param("dataTime") Date dataTime);
}