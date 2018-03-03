package com.jsmsframework.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsmsframework.common.entity.JsmsLog;
import com.jsmsframework.common.enums.LogConstant.LogType;
import com.jsmsframework.common.mapper.JsmsLogMapper;
import com.ucpaas.sms.common.util.StringUtils;

/**
 * 日志业务
 * 
 * @author xiejiaan
 */
@Service
public class JsmsLogServiceImpl implements JsmsLogService {
	private static final Logger logger = LoggerFactory.getLogger(JsmsLogServiceImpl.class);

	@Autowired
	private JsmsLogMapper logMapper;

	@Transactional("message")
	@Override
	public boolean addToOperation(LogType logType, String moduleName, String url, String ip, String userId,
			Object... desc) {
		JsmsLog log = new JsmsLog();
		log.setUserId(userId);
		log.setPageId(moduleName);
		log.setPageUrl(url);
		log.setIp(ip);
		log.setOpType(String.valueOf(logType.getValue()));
		log.setOpDesc(StringUtils.join(desc, ", "));

		int i = logMapper.addOperationLog(log);
		if (i > 0) {
			logger.debug("添加操作日志成功：" + log);
			return true;

		} else {
			logger.error("添加操作日志失败：" + log);
			return false;
		}
	}

	@Transactional("message")
	@Override
	public boolean add(LogType logType, String moduleName, String url, String ip, String userId, Object... desc) {
		JsmsLog log = new JsmsLog();
		log.setUserId(userId);
		log.setPageId(moduleName);
		log.setPageUrl(url);
		log.setIp(ip);
		log.setOpType(String.valueOf(logType.getValue()));
		log.setOpDesc(StringUtils.join(desc, ", "));

		int i = logMapper.addLog(log);
		if (i > 0) {
			logger.debug("添加操作日志成功：" + log);
			return true;

		} else {
			logger.error("添加操作日志失败：" + log);
			return false;
		}
	}
}
