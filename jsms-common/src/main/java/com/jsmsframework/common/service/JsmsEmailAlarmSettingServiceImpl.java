package com.jsmsframework.common.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.entity.JsmsEmailAlarmSetting;
import com.jsmsframework.common.mapper.JsmsEmailAlarmSettingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @description 邮件提醒设置表
 * @author tanjiangqiang
 * @date 2017-11-30
 */
@Service
public class JsmsEmailAlarmSettingServiceImpl implements JsmsEmailAlarmSettingService{

    @Autowired
    private JsmsEmailAlarmSettingMapper emailAlarmSettingMapper;
    
    @Override
	@Transactional
    public int insert(JsmsEmailAlarmSetting model) {
        return this.emailAlarmSettingMapper.insert(model);
    }

    @Override
	@Transactional
    public int insertBatch(List<JsmsEmailAlarmSetting> modelList) {
        return this.emailAlarmSettingMapper.insertBatch(modelList);
    }

	@Override
	@Transactional
    public int update(JsmsEmailAlarmSetting model) {
		JsmsEmailAlarmSetting old = this.emailAlarmSettingMapper.getById(model.getId());
		if(old == null){
			return 0;
		}
		return this.emailAlarmSettingMapper.update(model); 
    }

    @Override
	@Transactional
    public int updateSelective(JsmsEmailAlarmSetting model) {
		JsmsEmailAlarmSetting old = this.emailAlarmSettingMapper.getById(model.getId());
		if(old != null)
        	return this.emailAlarmSettingMapper.updateSelective(model);
		return 0;
    }

    @Override
	@Transactional
    public JsmsEmailAlarmSetting getById(Integer id) {
        JsmsEmailAlarmSetting model = this.emailAlarmSettingMapper.getById(id);
		return model;
    }

    @Override
	@Transactional
    public JsmsPage queryList(JsmsPage page) {
        List<JsmsEmailAlarmSetting> list = this.emailAlarmSettingMapper.queryList(page);
        page.setData(list);
        return page;
    }

    @Override
	@Transactional
    public int count(Map<String,Object> params) {
		return this.emailAlarmSettingMapper.count(params);
    }

    @Override
    public int countForEdit(Map<String, Object> params) {
        return this.emailAlarmSettingMapper.countForEdit(params);
    }
}
