package com.jsmsframework.order.entity.po;

import com.jsmsframework.order.entity.JsmsClientOrder;
import com.ucpaas.sms.common.util.DateUtils;

public class JsmsClientOrderPo extends JsmsClientOrder {
	private Integer returnQuantity;

	private String activePeriodDesc;

	private String endTimeStr;

	public Integer getReturnQuantity() {
		return returnQuantity;
	}

	public void setReturnQuantity(Integer returnQuantity) {
		this.returnQuantity = returnQuantity;
	}

	public String getActivePeriodDesc() {
		if (this.getActivePeriod() == null) {
			activePeriodDesc = "";
		}

		if (this.getActivePeriod() == 0) {
			activePeriodDesc = "无限期";
		} else {
			activePeriodDesc = this.getActivePeriod() + "天";
		}
		return activePeriodDesc;
	}

	public void setActivePeriodDesc(String activePeriodDesc) {
		this.activePeriodDesc = activePeriodDesc;
	}

	public String getEndTimeStr() {
		if (getEndTime() != null) {
			endTimeStr = DateUtils.formatDateTime(getEndTime());
		}
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}
}
