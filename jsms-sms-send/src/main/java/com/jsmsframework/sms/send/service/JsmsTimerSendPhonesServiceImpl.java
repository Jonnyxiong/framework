package com.jsmsframework.sms.send.service;

import com.jsmsframework.common.dto.JsmsPage;

import com.jsmsframework.common.util.DateUtil;
import com.jsmsframework.sms.send.entity.JsmsTimerSendPhones;
import com.jsmsframework.sms.send.mapper.JsmsTimerSendPhonesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @description 定时短信发送任务关联号码表-原表
 * @author Don
 * @date 2018-01-04
 */
@Service
public class JsmsTimerSendPhonesServiceImpl implements JsmsTimerSendPhonesService {

    @Autowired
    private JsmsTimerSendPhonesMapper timerSendPhonesMapper;
    
    @Override
    public int insert(JsmsTimerSendPhones model) {
        return this.timerSendPhonesMapper.insert(model);
    }

    @Override
    public int insertBatch(List<JsmsTimerSendPhones> modelList) {
        return this.timerSendPhonesMapper.insertBatch(modelList);
    }

	@Override
    public int update(JsmsTimerSendPhones model) {
		JsmsTimerSendPhones old = this.timerSendPhonesMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.timerSendPhonesMapper.update(model); 
    }

    @Override
    public int updateSelective(JsmsTimerSendPhones model) {
		JsmsTimerSendPhones old = this.timerSendPhonesMapper.getById(model.getId());
		if(old != null)
        	return this.timerSendPhonesMapper.updateSelective(model);
		return 0;
    }

    @Override
    public JsmsTimerSendPhones getById(Integer id) {
        JsmsTimerSendPhones model = this.timerSendPhonesMapper.getById(id);
		return model;
    }

    @Override
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsTimerSendPhones> list = this.timerSendPhonesMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
    public List<JsmsTimerSendPhones> findList(Map params) {
        List<JsmsTimerSendPhones> list = this.timerSendPhonesMapper.findList(params);
        return list;
    }

    @Override
    public int count(Map<String,Object> params) {
		return this.timerSendPhonesMapper.count(params);
    }

    /**
     * 根据提交创建时间+taskID查询，该任务下所有手机号
     * 以逗号分隔字符串返回
     *
     * @param submitTime
     * @param taskId
     * @return
     */
    @Override
    public String getAllPhone(Date submitTime, String taskId) {
        Date monday= DateUtil.getThisWeekMonday(submitTime);
        SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMdd");
        String mondayStr =formatter.format(monday);
        Integer tableDate=Integer.valueOf(mondayStr).intValue();

        return this.timerSendPhonesMapper.getAllPhone(tableDate,taskId);
    }
    /**
     * 根据提交创建时间+taskID查询，该任务下所有手机号
     * list返回
     * @param submitTime
     * @param taskId
     * @return
     */
    public List<String> getAllPhoneOfList(Date submitTime, String taskId){
        Date monday= DateUtil.getThisWeekMonday(submitTime);
        SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMdd");
        String mondayStr =formatter.format(monday);
        Integer tableDate=Integer.valueOf(mondayStr).intValue();
        return this.timerSendPhonesMapper.getAllPhoneOfList(tableDate,taskId);
    }
}
