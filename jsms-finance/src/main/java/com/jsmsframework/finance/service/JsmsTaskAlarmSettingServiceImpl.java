package com.jsmsframework.finance.service;

import com.jsmsframework.common.dto.JsmsPage;
import com.jsmsframework.common.enums.AlarmWebId;
import com.jsmsframework.common.enums.TaskType;
import com.jsmsframework.common.util.BeanUtil;
import com.jsmsframework.finance.entity.JsmsTaskAlarmSetting;
import com.jsmsframework.finance.enums.TaskAlarmType;
import com.jsmsframework.finance.mapper.JsmsTaskAlarmSettingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author huangwenjie
 * @description 提醒设置表
 * @date 2017-08-08
 */
@Service
public class JsmsTaskAlarmSettingServiceImpl implements JsmsTaskAlarmSettingService {
	private static Logger logger = LoggerFactory.getLogger(JsmsTaskAlarmSettingServiceImpl.class);

	@Autowired
	private JsmsTaskAlarmSettingMapper taskAlarmSettingMapper;

	@Override
	@Transactional
	public int insert(JsmsTaskAlarmSetting model) {
		this.convertScanFrequecyToMillis(model);
		return this.taskAlarmSettingMapper.insert(model);
	}

	@Override
	@Transactional
	public int insertBatch(List<JsmsTaskAlarmSetting> modelList) {
		for (JsmsTaskAlarmSetting model : modelList) {
			this.convertScanFrequecyToMillis(model);
		}
		return this.taskAlarmSettingMapper.insertBatch(modelList);
	}

	@Override
	@Transactional
	public int update(JsmsTaskAlarmSetting model) {
		JsmsTaskAlarmSetting old = this.taskAlarmSettingMapper.getById(model.getId());
		if (old == null) {
			return 0;
		}
		BeanUtil.copyProperties(model, old);
		this.convertScanFrequecyToMillis(model);
		return this.taskAlarmSettingMapper.update(model);
	}

	@Override
	@Transactional
	public int updateSelective(JsmsTaskAlarmSetting model) {
		JsmsTaskAlarmSetting old = this.taskAlarmSettingMapper.getById(model.getId());
		if (old != null) {
			BeanUtil.copyProperties(model, old);
			this.convertScanFrequecyToMillis(model);

			if (model.getScanFrequecy() != null)
			{
				// Add by lpjLiu 2017-12-2 提醒设置同时设置任务的扫描时间
				int oldSf = old.getScanFrequecy() / 60000;
				int newSf = model.getScanFrequecy().intValue() / 60000;
				if (oldSf != newSf) {
					String taskType = null;
					if (old.getTaskAlarmType().intValue() == TaskAlarmType.NUM_ALARM.getValue().intValue()) {
						if (old.getWebId().intValue() == AlarmWebId.品牌客户端.getValue().intValue())
						{
							taskType = TaskType.预付费客户余额预警.getValue().toString();
						} else if (old.getWebId().intValue() == AlarmWebId.OEM客户端.getValue().intValue()){
							taskType = TaskType.预付费OEM客户余额预警.getValue().toString();
						}

					} else if (old.getTaskAlarmType().intValue() == TaskAlarmType.AMOUNT_ALARM.getValue().intValue()) {
						if (old.getWebId().intValue() == AlarmWebId.代理商平台.getValue().intValue())
						{
							taskType = TaskType.代理商可用额度预警.getValue().toString();
						} else if (old.getWebId().intValue() == AlarmWebId.OEM代理商平台.getValue().intValue()){
							taskType = TaskType.OEM代理商可用额度预警.getValue().toString();
						}
					}

					if (taskType != null)
					{
						// 更新task的扫描周期
						this.taskAlarmSettingMapper.updateTaskExecutePeriod(String.valueOf(newSf), taskType);
					}
				}
			}
			return this.taskAlarmSettingMapper.updateSelective(model);
		}
		return 0;
	}

	@Override
	@Transactional
	public JsmsTaskAlarmSetting getById(Integer id) {
		JsmsTaskAlarmSetting model = this.taskAlarmSettingMapper.getById(id);
		this.convertScanFrequecyToSecord(model);
		return model;
	}

	@Override
	@Transactional
	public JsmsPage queryList(JsmsPage page) {
		List<JsmsTaskAlarmSetting> list = this.taskAlarmSettingMapper.queryList(page);
		for (JsmsTaskAlarmSetting model : list) {
			this.convertScanFrequecyToSecord(model);
		}

		page.setData(list);
		return page;
	}

	@Override
	@Transactional
	public int count(Map<String, Object> params) {
		return this.taskAlarmSettingMapper.count(params);
	}

	@Override
	public List<JsmsTaskAlarmSetting> getByTaskAlarmType(TaskAlarmType taskAlarmType) {
		JsmsTaskAlarmSetting taskAlarmSetting = null;
		JsmsPage page = new JsmsPage();
		page.getParams().put("taskAlarmType", taskAlarmType.getValue());
		List<JsmsTaskAlarmSetting> taskAlarmSettings = taskAlarmSettingMapper.queryList(page);
		/*
		 * if (taskAlarmSettings != null && taskAlarmSettings.size() > 1) {
		 * throw new JsmsTaskAlarmSettingException("提醒设置表数据异常"); }
		 * 
		 * if(taskAlarmSettings!=null && taskAlarmSettings.size() == 1){
		 * taskAlarmSetting = taskAlarmSettings.get(0); }
		 */

		return taskAlarmSettings;
	}

	/**
	 * 转换“检查频率”为毫秒 页面显示“检查频率”为分钟，数据库中保存为毫秒
	 * 
	 * @param model
	 */
	private void convertScanFrequecyToMillis(JsmsTaskAlarmSetting model) {
		// 页面显示“'检查频率”为分钟，数据库中保存为毫秒
		if (model.getScanFrequecy() != null) {
			logger.debug("转换“检查频率”为毫秒，转换前为 = {}", model.getScanFrequecy());
			model.setScanFrequecy(model.getScanFrequecy() * 1000 * 60);
			logger.debug("转换“检查频率”为毫秒，转换后为 = {}", model.getScanFrequecy());
		}
	}

	/**
	 * 转换“检查频率”为秒 页面显示“检查频率”为分钟，数据库中保存为毫秒
	 * 
	 * @param model
	 */
	private void convertScanFrequecyToSecord(JsmsTaskAlarmSetting model) {
		// 页面显示“'检查频率”为分钟，数据库中保存为毫秒
		if (model.getScanFrequecy() != null) {
			logger.debug("转换“检查频率”为秒，转换前为 = {}", model.getScanFrequecy());
			model.setScanFrequecy(model.getScanFrequecy() / 1000 / 60);
			logger.debug("转换“检查频率”为秒，转换后为 = {}", model.getScanFrequecy());
		}
	}
}
