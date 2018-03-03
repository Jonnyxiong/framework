package com.jsmsframework.finance.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.util.BeanUtil;
import com.jsmsframework.common.util.JsonUtil;
import com.jsmsframework.finance.entity.JsmsAgentBalanceAlarm;
import com.jsmsframework.finance.exception.JsmsAgentBalanceAlarmException;
import com.jsmsframework.finance.mapper.JsmsAgentBalanceAlarmMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @description 代理商余额预警信息表
 * @author huangwenjie
 * @date 2017-08-08
 */
@Service
public class JsmsAgentBalanceAlarmServiceImpl implements JsmsAgentBalanceAlarmService {

    private static Logger logger = LoggerFactory.getLogger(JsmsAgentBalanceAlarmService.class);
    @Autowired
    private JsmsAgentBalanceAlarmMapper agentBalanceAlarmMapper;
    
    @Override
	@Transactional
    public int insert(JsmsAgentBalanceAlarm model) {
        return this.agentBalanceAlarmMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsAgentBalanceAlarm> modelList) {
        return this.agentBalanceAlarmMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsAgentBalanceAlarm model) {
		JsmsAgentBalanceAlarm old = this.agentBalanceAlarmMapper.getById(model.getId());
		if(old == null){
		    BeanUtil.copyProperties(model,old);
			return 0;
		}
		return this.agentBalanceAlarmMapper.update(model); 
    }

    @Override
	@Transactional
    public int updateSelective(JsmsAgentBalanceAlarm model) {
		JsmsAgentBalanceAlarm old = this.agentBalanceAlarmMapper.getById(model.getId());
		if(old != null) {
            BeanUtil.copyProperties(model,old);
            return this.agentBalanceAlarmMapper.updateSelective(old);
        }
		return 0;
    }

    @Override
	@Transactional
    public int updateByAgentId(JsmsAgentBalanceAlarm model) {
        return this.agentBalanceAlarmMapper.updateByAgentId(model);
    }

    @Override
	@Transactional
    public JsmsAgentBalanceAlarm getById(Integer id) {
        JsmsAgentBalanceAlarm model = this.agentBalanceAlarmMapper.getById(id);
		return model;
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsAgentBalanceAlarm> list = this.agentBalanceAlarmMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
	@Transactional
    public List queryNeedAlarmList(Map params) {
        List<JsmsAgentBalanceAlarm> list = this.agentBalanceAlarmMapper.queryNeedAlarmList(params);
        return list;
    }

    @Override
    public List<JsmsAgentBalanceAlarm> findList(JsmsAgentBalanceAlarm model, Set agentIds) {
        return this.agentBalanceAlarmMapper.findList(model, agentIds);
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.agentBalanceAlarmMapper.count(params);
    }

    @Override
    @Transactional
    public int insertOrUpdate(JsmsAgentBalanceAlarm agentBalanceAlarm) {
        logger.debug("代理商修改告警信息{}", JsonUtil.toJson(agentBalanceAlarm));
        int row = 0;
        JsmsAgentBalanceAlarm dbData = null;

        if(agentBalanceAlarm.getId()!=null){  //通过id搜索
            dbData = getById(agentBalanceAlarm.getId());
        }else{
            if(agentBalanceAlarm.getAgentId()!=null)  //通过agentId搜索
                dbData = getByAgentId(agentBalanceAlarm.getAgentId());

        }
        Date now = new Date();
        if(dbData==null){ //不存在则插入
            agentBalanceAlarm.setCreateTime(now);
            agentBalanceAlarm.setUpdateTime(now);
            agentBalanceAlarm.setResetTime(now);
            agentBalanceAlarm.setReminderNumber(1);
            row = this.agentBalanceAlarmMapper.insert(agentBalanceAlarm);
        }else{
            if(!agentBalanceAlarm.getAlarmPhone().equals(dbData.getAlarmPhone())||agentBalanceAlarm.getAlarmAmount().compareTo(dbData.getAlarmAmount())!=0){
                BeanUtil.copyProperties(agentBalanceAlarm,dbData);
                dbData.setReminderNumber(1);
                dbData.setResetTime(null);
                dbData.setUpdateTime(now);
                row = this.agentBalanceAlarmMapper.updateSelective(dbData);
                logger.debug("代理商告警信息更新{}", JsonUtil.toJson(dbData));
            }
        }
        logger.debug("代理商修改告警信息完成,row={}", row);
        return row;
    }


    @Override
    @Transactional
    public JsmsAgentBalanceAlarm getByAgentId(Integer agentId) {
        JsmsAgentBalanceAlarm agentBalanceAlarm = null;

        JsmsPage page = new JsmsPage();
        page.getParams().put("agentId",agentId);
        List<JsmsAgentBalanceAlarm> agentBalanceAlarms = this.agentBalanceAlarmMapper.queryList(page);
        if(agentBalanceAlarms!=null&&agentBalanceAlarms.size()>1)
            throw new JsmsAgentBalanceAlarmException("可用余额告警数据异常");

        if(agentBalanceAlarms!=null&&agentBalanceAlarms.size()>0) {
            agentBalanceAlarm =  agentBalanceAlarms.get(0);
        }
        return agentBalanceAlarm;
    }
}
