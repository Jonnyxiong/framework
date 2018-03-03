package com.jsmsframework.common.mapper;

import org.springframework.stereotype.Repository;

import com.jsmsframework.common.entity.JsmsLog;

@Repository
public interface JsmsLogMapper {
	int addLog(JsmsLog log);

	int addOperationLog(JsmsLog log);
}