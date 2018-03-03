package com.jsmsframework.common.enums.invoice;

import java.util.Objects;

public enum InvoiceSourceEnum {
	// 来源方式：1. 发票申请2. 未开票客户返还发票
	发票申请(1, "发票申请"), 未开票客户返还发票(2, "未开票客户返还发票");

	private Integer value;
	private String desc;

	InvoiceSourceEnum(Integer value, String desc) {
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
		if (value == null) {
			return null;
		}
		String result = null;
		for (InvoiceSourceEnum s : InvoiceSourceEnum.values()) {
			if (Objects.equals(value, s.getValue())) {
				result = s.getDesc();
				break;
			}
		}
		return result;
	}
}
