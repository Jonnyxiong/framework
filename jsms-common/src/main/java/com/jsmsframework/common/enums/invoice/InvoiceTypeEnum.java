package com.jsmsframework.common.enums.invoice;

import java.util.Objects;

public enum InvoiceTypeEnum {
	// 发票类型，1：普通发票（电子）2：增值税专票
	普通发票(1, "普通发票"), 增值税专票(2, "增值税专票");

	private Integer value;
	private String desc;

	InvoiceTypeEnum(Integer value, String desc) {
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
		for (InvoiceTypeEnum s : InvoiceTypeEnum.values()) {
			if (Objects.equals(value, s.getValue())) {
				result = s.getDesc();
				break;
			}
		}
		return result;
	}
}
