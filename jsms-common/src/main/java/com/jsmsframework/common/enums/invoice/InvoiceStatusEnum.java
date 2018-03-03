package com.jsmsframework.common.enums.invoice;

import java.util.Objects;

public enum InvoiceStatusEnum {
	// 来源方式：1. 发票申请2. 未开票客户返还发票
	待审核(0, "待审核"),
	已取消(1, "已取消"),
	审核不通过(2, "审核不通过"),
	开票中(3, "开票中"),
	已邮寄(4, "已邮寄"),
	已返还(5, "已返还");

	private Integer value;
	private String desc;

	InvoiceStatusEnum(Integer value, String desc) {
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
		for (InvoiceStatusEnum s : InvoiceStatusEnum.values()) {
			if (Objects.equals(value, s.getValue())) {
				result = s.getDesc();
				break;
			}
		}
		return result;
	}
}
