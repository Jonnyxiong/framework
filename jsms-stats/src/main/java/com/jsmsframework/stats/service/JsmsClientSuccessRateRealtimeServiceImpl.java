package com.jsmsframework.stats.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.stats.entity.JsmsClientSuccessRateRealtime;
import com.jsmsframework.stats.mapper.JsmsClientSuccessRateRealtimeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @description 用户成功率数据统计表
 * @author huangwenjie
 * @date 2017-11-28
 */
@Service
public class JsmsClientSuccessRateRealtimeServiceImpl implements JsmsClientSuccessRateRealtimeService{

    @Autowired
    private JsmsClientSuccessRateRealtimeMapper clientSuccessRateRealtimeMapper;
    
    @Override
    public int insert(JsmsClientSuccessRateRealtime model) {
        return this.clientSuccessRateRealtimeMapper.insert(model);
    }

    @Override
    public int insertBatch(List<JsmsClientSuccessRateRealtime> modelList) {
        return this.clientSuccessRateRealtimeMapper.insertBatch(modelList);
    }

	@Override
    public int update(JsmsClientSuccessRateRealtime model) {
		JsmsClientSuccessRateRealtime old = this.clientSuccessRateRealtimeMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.clientSuccessRateRealtimeMapper.update(model); 
    }

    @Override
    public int updateSelective(JsmsClientSuccessRateRealtime model) {
		JsmsClientSuccessRateRealtime old = this.clientSuccessRateRealtimeMapper.getById(model.getId());
		if(old != null)
        	return this.clientSuccessRateRealtimeMapper.updateSelective(model);
		return 0;
    }

    @Override
    public JsmsClientSuccessRateRealtime getById(Long id) {
        JsmsClientSuccessRateRealtime model = this.clientSuccessRateRealtimeMapper.getById(id);
		return model;
    }

    @Override
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsClientSuccessRateRealtime> list = this.clientSuccessRateRealtimeMapper.queryList(page);
        page.setData(list);
        return page;
    }
    @Override
    public List<JsmsClientSuccessRateRealtime> queryList(Map params) {
        List<JsmsClientSuccessRateRealtime> list = this.clientSuccessRateRealtimeMapper.queryListByMap(params);
        return list;
    }
    @Override
    public List<JsmsClientSuccessRateRealtime> getLastOneStats(List<String> clientIds,Date date) {
        if(clientIds == null || clientIds.isEmpty()){
            return null;
        }else {
            List<JsmsClientSuccessRateRealtime> data = clientSuccessRateRealtimeMapper.getLastOneStats(clientIds,date);
            return data;
        }
    }

    @Override
    public int count(Map<String,Object> params) {
		return this.clientSuccessRateRealtimeMapper.count(params);
    }
    
}
