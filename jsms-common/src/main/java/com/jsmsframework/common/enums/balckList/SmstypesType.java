package com.jsmsframework.common.enums.balckList;

import java.util.Objects;

/**
 * Created by huangwenjie on 2017/12/01. 状态，
 1：有效，
 2：无效


 */
public enum SmstypesType {
	通知(1, "通知"), 验证码(2, "验证码"), 营销(4, "营销"), 告警(8, "告警"), USSD(16, "USSD"), 闪信(32, "闪信");

	private Integer value;
	private String desc;

	SmstypesType(Integer value, String desc) {
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
		for (SmstypesType s : SmstypesType.values()) {
			if (Objects.equals(value,s.getValue())) {
				result = s.getDesc();
				break;
			}
		}
		return result;
	}

}
