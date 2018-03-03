package com.jsmsframework.common.enums.balckAndWhiteTemplate;

/**
 * Created by lpjLiu on 2017/7/10. 智能能模版提交状态
 */
public enum AutoTemplateStatus {
	待审核(0, "待审核"), 审核通过(1, "审核通过"), 审核不通过(3, "审核不通过"), 删除(4, "删除");

	private Integer value;
	private String desc;

	AutoTemplateStatus(Integer value, String desc) {
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
		for (AutoTemplateStatus s : AutoTemplateStatus.values()) {
			if (value == s.getValue()) {
				result = s.getDesc();
				break;
			}
		}
		return result;
	}
}
