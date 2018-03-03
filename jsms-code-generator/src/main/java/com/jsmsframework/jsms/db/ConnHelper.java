package com.jsmsframework.jsms.db;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jsmsframework.jsms.dto.ConnDTO;
import com.jsmsframework.util.StringUtil;

/**
 * 数据库连接
 * 
 * @author huangwenjie
 * @date 2017-01-24
 */
public class ConnHelper {

	private static Logger logger = LoggerFactory.getLogger(ConnHelper.class);

	public static Connection getConnection() {
		Properties properties = new Properties();

		String dbConfig = "./db.properties";
		logger.debug("加载配置文件{}", dbConfig);
		try {
			properties.load(ConnHelper.class.getClassLoader().getResourceAsStream(dbConfig));
		} catch (IOException e) {
			logger.error("加载配置文件{}失败", dbConfig);
			return null;
		}

		ConnDTO connDTO = new ConnDTO();
		connDTO.setUrl(StringUtil.trim(properties.get("url")));
		connDTO.setUsername(StringUtil.trim(properties.get("username")));
		connDTO.setPassword(StringUtil.trim(properties.get("password")));

		String database = StringUtil.toUpper((properties.get("database")));

		logger.debug("使用数据库信息:");
		logger.debug("url={}", connDTO.getUrl());
		logger.debug("username={}", connDTO.getUsername());
		logger.debug("passwrod={}", connDTO.getPassword());
		logger.debug("database={}", database);
		try {
			if ("MYSQL".equals(database)) {
				Class.forName("com.mysql.jdbc.Driver");
			} else if ("ORACLE".equals(database)) {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} else {
				logger.debug("database配置错误，支持MYSQL或ORACLE");
				return null;
			}

		} catch (ClassNotFoundException e) {
			logger.error("{}的驱动包加载异常", database);
		}
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(connDTO.getUrl(), connDTO.getUsername(), connDTO.getPassword());
		} catch (SQLException e) {
			logger.error("获取数据库连接connection异常");
		}

		return conn;
	}

}
