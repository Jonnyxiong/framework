package com.jsmsframework.common.service;

import com.jsmsframework.common.enums.LogConstant.LogType;

/**
 * 日志业务
 * 
 * @author xiejiaan
 */
public interface JsmsLogService {

	/**
	 * 添加操作日志-运营平台专用
	 * 
	 * @param logType
	 *            日志类型
	 * @param userId
	 *            用户ID
	 * @param moduleName
	 *            模块
	 * @param url
	 * @param ip
	 * @param desc
	 *            描述，可传多个
	 * @return
	 */
	boolean addToOperation(LogType logType, String moduleName, String url, String ip, String userId, Object... desc);

	/**
	 * 添加操作日志
	 *
	 * @param logType
	 *            日志类型
	 * @param userId
	 *            用户ID
	 * @param moduleName
	 *            模块
	 * @param url
	 * @param ip
	 * @param desc
	 *            描述，可传多个
	 * @return
	 */
	boolean add(LogType logType, String moduleName, String url, String ip, String userId, Object... desc);

}
