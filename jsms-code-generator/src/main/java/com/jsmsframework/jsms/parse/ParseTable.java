package com.jsmsframework.jsms.parse;

import java.sql.Connection;

import com.jsmsframework.jsms.dto.PackageDTO;

/**
 * 解析数据库中的表
 * 
 * @author huangwenjie
 * @date 2017-01-24
 */
public interface ParseTable {

	/**
	 * @title 获取表的信息
	 * @param
	 * @return
	 * @throws Exception 
	 */
	public PackageDTO generatePojoByTable(Connection conn, PackageDTO packageDTO) throws Exception;

}
