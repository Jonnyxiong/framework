package com.jsmsframework.order.mapper;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.order.entity.JsmsOemClientPool;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description OEM代理商客户短信池
 * @author huangwenjie
 * @date 2017-08-16
 */
@Repository
public interface JsmsOemClientPoolMapper{

	int insert(JsmsOemClientPool model);
	
	int insertBatch(List<JsmsOemClientPool> modelList);

	int update(JsmsOemClientPool model);

	int updateRemainNumber(JsmsOemClientPool model);

	int updateSelective(JsmsOemClientPool model);

    JsmsOemClientPool getByClientPoolId(Long clientPoolId);

	List<JsmsOemClientPool> queryList(JsmsPage<JsmsOemClientPool> page);

	int count(Map<String,Object> params);

    JsmsOemClientPool getByClientPoolInfo(JsmsOemClientPool model);

    List<JsmsOemClientPool> getListByClientPoolInfo(JsmsOemClientPool model);

    /**
      * @Description: 查询子账户剩余短信数量详情(过滤剩余数量小于0)
      * @Author: tanjiangqiang
      * @Date: 2018/1/15 - 17:18
      * @param model
      *
      */
    List<JsmsOemClientPool> queryRemainQuantityClientPoolInfo(JsmsOemClientPool model);

//    List<JsmsOemClientPool> getListByPoolInfoAndDueTimeRange(JsmsOemClientPool model, @Param("beginStartTime")Date beginStartTime, @Param("endStartTime")Date endStartTime);
    List<JsmsOemClientPool> getListByPoolInfoAndDueTimeRange(Map params);

    void updateForAddClientPoolRemainNum(JsmsOemClientPool model);

	int updateForReduceClientPoolRemainNum(Map<String, Object> model);

	List<JsmsOemClientPool> findSUMTotal(@Param("clientIds") Set clientIds);

	int updateClientPoolByCondition(@Param("test_num") int test_num,@Param("client_pool_id") long client_pool_id);
}