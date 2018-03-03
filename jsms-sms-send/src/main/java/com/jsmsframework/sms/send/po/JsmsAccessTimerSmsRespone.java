package com.jsmsframework.sms.send.po;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;
import java.util.Map;

@JsonInclude(Include.NON_NULL)
public class JsmsAccessTimerSmsRespone {

	private static final long serialVersionUID = 3947127755167515787L;
	/**
	 * 短信发送的计费总条数
	 */
	private Integer total_fee;
	/**
	 * 短信标识符(sid + 手机号用于匹配状态报告)
	 */
	private String sid;
	/**
	 * 短信标识符(sid + 手机号用于匹配状态报告)
	 */
	private String uid;
	/**
	 * 返回号码列表使用的压缩算法。 默认gzip:	 0: gzip
	 */
	private String comporess_type;
	/**
	 * 发送的详细情况
	 */
	private List<Map<String, String>> data;
	/**
	 * 短信请求响应返回码，参考“请求响应返回码”定义的返回码
	 */
	private Integer code;
	/**
	 * 短信请求响应返回中文描述，	 参考“请求响应返回码”定义的中文描述
	 */
	private String msg;
	/**
	 * 当code不为0时存在，表示code对应的号码列表(同样使用compress_type指定类型压缩+base64编码)；
	 * code为0时无此域；
	 * 除去code不为0对应的号码，剩下的号码都对应为0的code，但不在此列出
	 */
	private String mobilelist;
	/**
	 * 此元组code对应的号码个数
	 */
	private Integer mobilecnt;

	public Integer getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(Integer total_fee) {
		this.total_fee = total_fee;
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getComporess_type() {
		return comporess_type;
	}

	public void setComporess_type(String comporess_type) {
		this.comporess_type = comporess_type;
	}

	public List<Map<String, String>> getData() {
		return data;
	}

	public void setData(List<Map<String, String>> data) {
		this.data = data;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMobilelist() {
		return mobilelist;
	}

	public void setMobilelist(String mobilelist) {
		this.mobilelist = mobilelist;
	}

	public Integer getMobilecnt() {
		return mobilecnt;
	}

	public void setMobilecnt(Integer mobilecnt) {
		this.mobilecnt = mobilecnt;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
