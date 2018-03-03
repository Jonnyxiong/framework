package com.jsmsframework.common.enums;

/**
 * Created by lpjLiu on 2017/10/10
 */
public enum TaskSysType {
	默认任务系统(0, "默认任务系统"), 财务任务系统(1, "财务任务系统");

	private Integer value;
	private String desc;

	TaskSysType(Integer value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public Integer getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}

	public static String getDescByValue(Integer value) {
		if(value == null){ return null;}
		String result = null;
		for (TaskSysType s : TaskSysType.values()) {
			if (value == s.getValue()) {
				result = s.getDesc();
				break;
			}
		}
		return result;
	}
}
