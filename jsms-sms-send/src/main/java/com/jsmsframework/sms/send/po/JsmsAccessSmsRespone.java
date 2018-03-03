package com.jsmsframework.sms.send.po;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;
import java.util.Map;

@JsonInclude(Include.NON_NULL)
public class JsmsAccessSmsRespone {

	private static final long serialVersionUID = 3947127755167515787L;

	private int total_fee;

	private List<Map<String, String>> data;

	public JsmsAccessSmsRespone(){
		super();
	}

	public JsmsAccessSmsRespone(int total_fee, List<Map<String, String>> data){
		super();
		this.total_fee = total_fee;
		this.data = data;
	}
	
	public int getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(int total_fee) {
		this.total_fee = total_fee;
	}

	public List<Map<String, String>> getData() {
		return data;
	}

	public void setData(List<Map<String, String>> data) {
		this.data = data;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
