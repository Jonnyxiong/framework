package com.jsmsframework.common.constant;

/**
 * 系统常量
 * 2017年11月29日
 * @author Don
 */
public class SysConstant {
	//result status 失败常量
	public static final String FAIL="fail";
	//result status 成功常量
	public static final String SUCCESS="success";
	//result status 数据过期常量
	public static  final String OVERDUE="overdue";

	public static final Integer FAIL_CODE=500;

	public static final Integer SUCCESS_CODE=0;

	public static final Integer OVERDUE_CODE=405;
	//系统操作ID
	public static final Long SYS_ID=0L;

	//确认是否需要删除
	public static final Integer SUER_DEL_CODE=666;
	//删除码
	public static final Integer DEL_CODE=667;
	//*代表所有，用于clientId等
	public static final String  ALL="*";
	//全局模板数量，智能黑白模板分开计算，单位：条
	public static final String GLOBAL_TEMPLATE_NUMBER="GLOBAL_TEMPLATE_NUMBER";
	//定时短信发送配置  周期+最小时长+最大时长+数量+设置最小间隔+取消最小间隔
	public static final String TIMER_SEND_CFG="TIMER_SEND_CFG";

	/**
	 * 4.4版本 投诉率相关常量 开始
	 */
	//短信记录流水表（t_sms_record_yyyyMMdd）
	public static  final String T_SMS_RECORD_="t_sms_record_";
	//系统黑名单 redis key前缀+号码
	public static final String 	BLACK_LIST_KEY_PREFIX="black_list:";
	//系统黑名单所在redis分区
	public static final Integer BLACK_LIST_DB=5;
	// 系统黑名单为全局黑名单 ,如果黑名单只有系统级别的保存为 0&
	public static final String SYS_BLACK_LIST_CHANNEID="0";

	/**
	 * 4.4版本 投诉率相关Redis常量 结束
	 */


	/**
	 * 边发边审Redis常量 开始
	 */
	//边发边审拦截明细DB
	public static final Integer SEND_AUDIT_INTERCEPT_DB=1;
	//边发边审拦截明细key前缀
	public static final String SEND_AUDIT_INTERCEPT_KEY_PREFIX="send_audit_intercept:";
	/**
	 * 边发边审Redis常量 结束
 	*/
	/**
	 * 重推状态报告Redis常量 开始
	 */
	//标准协议
	public static final Integer STANDARD_PROTOCOL_DB=3;
	public static final String  STANDARD_PROTOCOL_KEY_PREFIX="report_cache:";
	//HTTPS协议（主动拉取）
	public static final Integer HTTPS_PROTOCOL_DB=4;
	public static final String  HTTPS_PROTOCOL_KEY_PREFIX="active_report_cache:";
	/**
	 * 重推状态报告Redis常量 结束
	 */

	/**
	 * 审核相关Redis常量 开始
	 */
	public static final Integer SMS_AUDIT_LOCKED_DB=6;

	public static final String  SMS_AUDIT_LOCKED_KEY_PREFIX="SMSAuditLocked:";


	/**
	 * 审核相关Redis常量 结束
	 */

}
